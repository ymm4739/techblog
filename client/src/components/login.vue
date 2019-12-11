<template>
  <div class="login">
    <el-form :model="form"
             label-position="left"
             label-width="60px"
             :rules="rules"
             status-icon>
      <el-form-item label="登陆名"
                    prop="loginName">
        <el-input v-model="form.loginName"
                  placeholder="请输入电子邮箱或者用户名"
                  clearable></el-input>
      </el-form-item>
      <el-form-item label="密码"
                    prop="password">
        <el-input v-model="form.password"
                  placeholder="请输入密码"
                  show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="form.isRememberMe"
                     style="float:left">记住我</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   @click="submit">登陆</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   @click="$router.push({name: 'registry'})">注册</el-button>
        <el-button type="primary"
                   @click="resetpassword">忘记密码</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Login',
  data () {
    return {
      form: {
        loginName: '',
        password: '',
        isRememberMe: false
      },
      rules: {
        loginName: [
          {
            require: true,
            message: '请输入用户名或者邮箱地址',
            trigger: 'blur'
          }
        ],
        password: [
          { require: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度为6位及以上', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapGetters([
      'token'
    ])
  },
  methods: {
    submit: function () {
      let data = {
        'loginName': this.form.loginName,
        'password': this.form.password,
        'isRememberMe': this.form.isRememberMe
      }
      this.$store.dispatch('user/login', data).then(res => {
        let redirect = this.$route.query.redirect // $route 获取路由信息
        if (redirect === location.hostname) {
          this.$router.go(-1)
        } else {
          this.$router.push({ name: 'home' })
        }
      })
    },
    resetpassword: function () {
      console.log('重置密码')
    }
  }
}
</script>
<style scoped>
.login {
  margin: 300px;
}
</style>
