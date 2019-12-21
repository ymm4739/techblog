<template>
  <div>
    <el-form :model="form"
             :rules="rules"
             show-status>
      <el-form-item label="标题"
                    prop="title">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="摘要"
                    prop="summary">
        <el-input type="textarea"
                  v-model="form.summary"></el-input>
      </el-form-item>
      <mavon-editor v-model="form.content"
                    ref="md"
                    @change="change"></mavon-editor>

      <el-form-item>
        <el-button type="primary"
                   @click="submit">保存</el-button>
        <el-button @click="cancle">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { create, update, show } from '@/api/article'
import { Message } from 'element-ui'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
  name: 'ArticleEditView',
  components: {
    mavonEditor
  },
  data () {
    return {
      form: {
        title: '',
        content: '',
        abstract: ''
      },
      html: '',
      rules: {
        title: [{
          required: true,
          message: '输入文章标题',
          trigger: 'blur'
        }],
        summary: [{
          required: true,
          message: '输入文章摘要',
          trigger: 'blur'
        }]
      },
      urlPrefix: '/user/' + this.$route.params.userID + '/article/',
      userID: this.$route.params.userID
    }
  },
  computed: {
    isUpdated () {
      return !!this.$route.params.articleID
    }
  },
  created () {
    if (this.isUpdated) {
      let id = this.$route.params.articleID
      let userID = this.$route.params.userID
      show(userID, id).then(res => {
        this.form = res.data
      })
    }
  },
  methods: {
    cancle () {
      this.$router.push({ path: this.urlPrefix + 'list' })
    },
    submit () {
      let data = {
        title: this.form.title,
        content: this.form.content,
        summary: this.form.summary,
        html: this.html
      }
      if (this.isUpdated) {
        let id = this.$route.params.articleID
        update({ id, data }, this.userID).then(res => {
          Message.success(res.message)
          this.$router.push({ path: this.urlPrefix + 'list' })
        }).catch(() => { })
      } else {
        create(data, this.userID).then(res => {
          Message.success(res.message)
          this.$router.push({ path: this.urlPrefix + 'list' })
        }).catch(() => { })
      }
    },
    change (value, render) {
      this.html = render
    }
  }
}
</script>
