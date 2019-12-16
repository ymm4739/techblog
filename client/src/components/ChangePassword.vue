<template>
  <el-dialog title="修改密码"
             center
             :visible.sync="show"
             :modal="false"
             width="30%"
             :close-on-click-modal="false"
             @close="closeDialog">
    <el-form ref="form"
             :rules="rules"
             :model="form"
             size="mini"
             label-width="80px"
             label-position="left"
             status-icon>
      <el-form-item label="原密码"
                    prop="oldPassword">
        <el-input v-model="form.oldPassword"
                  placeholder="原密码"
                  show-password>
        </el-input>
      </el-form-item>
      <el-form-item label="新密码"
                    prop="newPassword">
        <el-input v-model="form.newPassword"
                  placeholder="新密码"
                  show-password>
        </el-input>
      </el-form-item>
    </el-form>
    <span slot="footer">
      <el-button type="primary"
                 @click="confirm">确定
      </el-button>
      <el-button type="primary"
                 plain
                 @click="cancel">取消</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { Message } from 'element-ui'
export default {
  name: 'ChangePassword',
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
      show: this.visible,
      form: {
        oldPassword: '',
        newPassword: ''
      },
      rules: {
        oldPassword: [
          {
            required: true,
            message: '请输入原密码',
            trigger: 'blur'
          },
          {
            min: 6,
            message: '密码长度至少为6位',
            trigger: 'blur'
          }
        ],
        newPassword: [
          {
            required: true,
            message: '请输入新密码',
            trigger: 'blur'
          },
          {
            min: 6,
            message: '密码长度至少为6位',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  watch: {
    visible () {
      this.show = this.visible
    }
  },
  methods: {
    confirm () {
      this.$store.dispatch('user/changePassword', this.form).then(res => {
        Message.success('更改密码成功')
        this.closeDialog()
        this.$emit('update:loginVisible', true)
      }).catch(error => {
        Message({
          message: '更改密码失败,' + error,
          type: 'info',
          duration: 10 * 1000
        })
      })
    },
    cancel () {
      this.closeDialog()
    },
    closeDialog () {
      this.form.oldPassword = ''
      this.form.newPassword = ''
      this.$emit('update:visible', false)
    }
  }
}
</script>
