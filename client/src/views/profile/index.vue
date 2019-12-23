<template>
  <div class="profile">
    <el-upload class="avatar-uploader"
               action="http://localhost:8080/api/user/change/avatar"
               :http-request="customUpload"
               :show-file-list="false">
      <img v-if="avatar"
           :src="avatar"
           class="avatar">
      <i v-else
         class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
    <el-form>
      <el-form-item label="用户名">{{user.username}}
        <el-input v-if="modify"
                  :placeholder="username">
        </el-input>
      </el-form-item>
      <el-form-item label="邮箱">{{user.email}}
        <el-input v-if="modify"
                  :placeholder="email"></el-input>
        <el-button v-if="!activated"
                   type="primary"
                   @click="activate">激活邮箱</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   @click="update">修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { Message } from 'element-ui'
export default {
  name: 'Profile',
  data () {
    return {
      user: this.$store.getters.user,
      modify: false,
      activated: this.$store.state.user.isValidEmail
    }
  },
  created () {
    if (this.username === '') {
      this.$store.dispatch('user/getInfo')
    }
  },
  computed: {
    username () {
      return this.$store.state.user.username
    },
    email () {
      return this.$store.state.user.email
    },
    avatar () {
      return this.$store.state.user.avatar
    }

  },
  methods: {
    update () {
      this.modify = true
    },
    activate () {
      let data = {
        email: this.email
      }
      this.$store.dispatch('user/sendActivatedEmail', data).then(res => {
        Message.success(res.message)
      })
    },
    customUpload (file) {
      console.log(file)
      this.$store.dispatch('user/changeAvatar', file).then(res => {

      })
    }
  }
}
</script>
<style scoped>
.profile {
  top: 10%;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
