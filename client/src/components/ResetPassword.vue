<template>
  <el-dialog :visible.sync="show"
             :modal="false"
             :close-on-click-modal="false"
             center
             width="30%"
             title="重置密码"
             @close="closeDialog">
    <div>
      <el-form :model="form"
               size="mini"
               label-position="left"
               label-width="80px"
               status-icon
               :rules="rules">
        <el-form-item label="邮箱"
                      prop="email">
          <el-input v-model="form.email"
                    placeholder="注册的邮箱地址"></el-input>
        </el-form-item>
        <el-form-item label="验证码"
                      prop="verifyCode">
          <el-input v-model="form.verifyCode"
                    placeholder="验证码"></el-input>
          <el-button type="primary"
                     @click="sendEmail">获取验证码</el-button>
        </el-form-item>
        <el-form-item label="新密码"
                      prop="password">
          <el-input show-password
                    v-model="form.password"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer">
      <el-button type="primary"
                 @click="confirm">确定</el-button>
      <el-button type="primary"
                 plain
                 @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { resetPasswordByEmail, getVerifyCode } from '@/api/user'
import { Message } from 'element-ui'
export default {
  name: 'ResetPassword',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    loginVisible: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      form: {
        email: '',
        verifyCode: '',
        password: ''
      },
      rules: {
        email: [
          { required: true, type: 'email', message: '邮箱地址格式不正确', trigger: 'blur' }
        ],
        verifyCode: [
          { required: true, message: '验证码为数字', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码为必填项', trigger: 'blur' }
        ]
      },
      show: this.visible,
      rawCode: ''
    }
  },
  watch: {
    visible () {
      this.show = this.visible
    }
  },
  methods: {
    closeDialog () {
      this.form.email = ''
      this.form.password = ''
      this.form.verifyCode = ''
      this.rawCode = ''
      this.$emit('update:visible', false)
      this.$emit('update:loginVisible', true)
    },
    confirm () {
      let data = {
        email: this.form.email,
        password: this.form.password,
        verifyCode: this.form.verifyCode,
        rawCode: this.rawCode
      }
      resetPasswordByEmail(data).then(res => {
        Message.success(res.message)
        this.closeDialog()
      }).catch(() => { })
    },
    cancel () {
      this.closeDialog()
    },
    sendEmail () {
      let data = {
        email: this.form.email
      }
      getVerifyCode(data).then(res => {
        Message.success(res.message)
        this.rawCode = res.data.code || ''
      }).catch(() => { })
    }
  }
}
</script>
