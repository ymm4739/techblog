<template>
  <div class="registry">
    <el-dialog center
               :visible="show"
               title="用户注册"
               width="30%"
               :modal="modal"
               :before-close="handlerClose">
      <el-form :model="form"
               label-position="left"
               label-width="60px"
               :rules="rules"
               status-icon
               size="mini">
        <el-form-item label="用户名"
                      prop="username">
          <el-input v-model="form.username"
                    placeholder="请输入用户名"></el-input>
        </el-form-item>
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
      </el-form>
      <div slot="footer">
        <el-button type="primary"
                   @click="submit">注册</el-button>
        <el-button type="primary"
                   @click="login">登陆
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { registry } from '@/api/user'
export default {
  name: 'Registry',
  props: [
    'visible'
  ],
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
      },
      modal: false,
      show: this.visible
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
          this.$emit('show-login-dialog')
          this.$emit('close-registry-dialog')
        }
        )
    },
    login () {
      this.$emit('show-login-dialog')
      this.$emit('close-registry-dialog')
    },
    handlerClose (done) {
      this.$emit('close-registry-dialog')
    }
  }
}
</script>
<style scoped>
.login {
  margin: 300px;
}
</style>
