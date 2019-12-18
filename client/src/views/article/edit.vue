<template>
  <div>
    <el-form :model="form"
             :rules="rules"
             show-status>
      <el-form-item label="标题"
                    prop="title">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="内容"
                    prop="content">
        <el-input type="textarea"
                  v-model="form.content"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   @click="submit">保存</el-button>
        <el-button @click="$router.push({path:'/article/list'})">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { create } from '@/api/article'
import { Message } from 'element-ui'
export default {
  name: 'ArticleEditView',
  data () {
    return {
      form: {
        title: '',
        content: ''
      },
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
  methods: {
    submit () {
      let data = {
        title: this.form.title,
        content: this.form.content
      }
      create(data).then(res => {
        Message.success(res.message)
        this.$router.push({ path: '/article/list' })
      }).catch(() => { })
    }
  }
}
</script>
