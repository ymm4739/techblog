<template>
  <div>
    <el-dialog title="登陆博客网站"
               :visible.sync="visible"
               center
               width="30%"
               :modal="modal"
               :close-on-click-modal="false"
               :before-close="handlerClose">
      <el-form :model="form"
               class="login"
               label-position="left"
               label-width="60px"
               :rules="rules"
               status-icon
               size="mini">
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
          <el-button type="text"
                     @click="resetPassword">忘记密码</el-button>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="form.isRememberMe"
                       style="float:left">记住我</el-checkbox>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary"
                   @click="submit">登陆</el-button>
        <el-button type="primary"
                   @click="registry">注册</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'Login',
  inject: ['reload'],
  props: [
    'show'
  ],
  watch: {
    show () {
      this.visible = this.show
    }
  },
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
      },
      modal: false,
      visible: this.show
    }
  },
  computed: {

  },
  methods: {
    submit: function () {
      let data = {
        'loginName': this.form.loginName,
        'password': this.form.password,
        'isRememberMe': this.form.isRememberMe
      }
      this.$store.dispatch('user/login', data).then(res => {
        // this.$router.push({ path: this.$route.fullPath })
        // this.reload()
        location.reload()
        this.closeDialog()
      })
    },
    registry () {
      this.$emit('show-registry-dialog')
      this.closeDialog()
    },
    resetPassword: function () {

    },
    handlerClose (done) {
      this.closeDialog()
      done()
    },
    closeDialog () {
      this.$emit('close-login-dialog')
    }
  }
}
</script>
<style scoped>
.login {
  margin: auto;
}
</style>
