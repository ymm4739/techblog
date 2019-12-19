<template>
  <div>
    <el-form :model="form"
             :rules="rules"
             show-status>
      <el-form-item label="标题"
                    prop="title">
        <el-input v-model="form.title"></el-input>
      </el-form-item>

      <mavon-editor v-model="form.content"
                    ref="md"
                    @change="change"></mavon-editor>

      <el-form-item>
        <el-button type="primary"
                   @click="submit">保存</el-button>
        <el-button @click="$router.push({path:'/article/list'})">取消</el-button>
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
        content: ''
      },
      html: '',
      rules: {
        title: [{
          required: true,
          message: '输入文章标题',
          trigger: 'blur'
        }],
        content: [{
          required: true,
          message: '输入文章内容',
          trigger: 'blur'
        }]
      }
    }
  },
  computed: {
    isUpdated () {
      return !!this.$route.params.id
    }
  },
  created () {
    if (this.isUpdated) {
      let id = this.$route.params.id
      show(id).then(res => {
        this.form = res.data
      })
    }
  },
  methods: {
    submit () {
      let data = {
        title: this.form.title,
        content: this.form.content,
        html: this.html
      }
      if (this.isUpdated) {
        let id = this.$route.params.id
        update({ id, data }).then(res => {
          Message.success(res.message)
          this.$router.push({ path: '/article/list' })
        }).catch(() => { })
      } else {
        create(data).then(res => {
          Message.success(res.message)
          this.$router.push({ path: '/article/list' })
        }).catch(() => { })
      }
    },
    change (value, render) {
      this.html = render
    }
  }
}
</script>
