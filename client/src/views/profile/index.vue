<template>
  <el-main>
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
        </el-form-item>
        <el-form-item>
          <el-button type="primary"
                     @click="update">修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-main>
</template>
<script>
import { Message } from 'element-ui'
import { profile } from '@/api/user'
export default {
  name: 'Profile',
  data () {
    return {
      user: '',
      modify: false,
      activated: this.$store.state.user.isValidEmail,
      userID: this.$route.params.userID
    }
  },
  created () {
    this.fetchData()
  },
  computed: {
    username () {
      return this.user.username
    },
    email () {
      return this.user.email
    },
    avatar () {
      return this.user.avatar
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
    },
    fetchData () {
      profile(this.userID).then(res => {
        this.user = res.data
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
