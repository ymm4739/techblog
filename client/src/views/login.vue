<template>
  <el-container>
    <el-main>
      <div>
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
          <el-form-item>
            <el-button type="primary"
                       @click="submit">登陆</el-button>
            <el-button type="primary"
                       @click="registry">注册</el-button>
          </el-form-item>
        </el-form>
        <registry-dialog :visible.sync="visible" />
        <reset-password :visible.sync="resetPasswordVisible" />
      </div>
    </el-main>
  </el-container>
</template>
<script>
import RegistryDialog from '@/components/registry'
import ResetPassword from '@/components/ResetPassword'
export default {
  name: 'loginView',
  components: {
    RegistryDialog,
    ResetPassword
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
      visible: false,
      resetPasswordVisible: false
    }
  },
  methods: {
    submit () {
      let data = {
        'loginName': this.form.loginName,
        'password': this.form.password,
        'isRememberMe': this.form.isRememberMe
      }
      this.$store.dispatch('user/login', data).then(res => {
        let redirect = this.$route.query.redirect
        this.$router.push({ path: redirect })
      })
    },
    registry () {
      this.visible = true
    },
    resetPassword () {
      this.resetPasswordVisible = true
    }
  }
}
</script>
