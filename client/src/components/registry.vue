<template>
  <div class="registry">
    <el-form :model="form"
             label-position="left"
             label-width="60px"
             :rules="rules"
             status-icon>
      <el-form-item label="邮箱"
                    prop="email">
        <el-input v-model="form.email"
                  placeholder="请输入电子邮箱"
                  clearable></el-input>
      </el-form-item>
      <el-form-item label="密码"
                    prop="password">
        <el-input v-model="form.password"
                  placeholder="请输入密码"
                  show-password></el-input>
      </el-form-item>
      <el-form-item label="用户名"
                    prop="username">
        <el-input v-model="form.username"
                  placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   @click="submit">注册</el-button>
        <el-button type="primary"
                   @click="login">
          登陆
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
// import qs from 'qs'
import { registry } from '@/api/user'
export default {
  name: 'Registry',
  data () {
    return {
      form: {
        email: '',
        password: '',
        username: ''
      },
      rules: {
        email: [
          {
            require: true,
            type: 'email',
            message: '请输入正确格式邮箱地址',
            trigger: 'blur'
          }
        ],
        password: [
          { require: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度为6位及以上', trigger: 'blur' }
        ],
        username: [
          { require: true, message: '用户名不能为空', trigger: 'blue' }
        ]
      }
    }
  },
  methods: {
    submit: function () {
      let data = {
        username: this.form.username,
        email: this.form.email,
        password: this.form.password
      }
      registry(data)
        .then(res => {
          this.$router.push({ name: 'login' })
        }
        )
    },
    login () {
      this.$router.push({ name: 'login' })
    }
  }
}
</script>
<style scoped>
.login {
  margin: 300px;
}
</style>
