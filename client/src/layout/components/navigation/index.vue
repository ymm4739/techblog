<template>
  <div>
    <el-menu :default-active="$route.path"
             mode="horizontal"
             background-color="black"
             text-color="white"
             router
             active-text-color="yellow">
      <el-menu-item index="/home">首页</el-menu-item>
      <el-menu-item index="/post">文章</el-menu-item>
      <el-menu-item :index="adminHome"
                    v-if="login">管理</el-menu-item>
      <el-menu-item index=""
                    v-if="!login"
                    class="nav-login"
                    @click="showLoginDialog">登陆</el-menu-item>
      <el-submenu v-if="login"
                  class="nav-user"
                  index="-1">
        <template slot="title">
          <el-avatar :src="avatar"
                     @error="true">{{ username }}</el-avatar>
        </template>
        <el-menu-item :index="articleIndex">我的博客</el-menu-item>
        <el-menu-item :index="userProfile">个人资料</el-menu-item>
        <el-menu-item index=""
                      @click="changePassword">修改密码</el-menu-item>
        <el-menu-item index=""
                      @click="logout">退出登陆</el-menu-item>
      </el-submenu>
    </el-menu>

    <Login :visible.sync="loginVisible"
           :registryVisible.sync="registryVisible"
           :resetPasswordVisible.sync="resetPasswordVisible"></Login>
    <Registry :visible.sync="registryVisible"
              :loginVisible.sync="loginVisible"></Registry>
    <change-password :visible.sync="changePasswordVisible"
                     :loginVisible.sync="loginVisible"></change-password>
    <reset-password :visible.sync="resetPasswordVisible"
                    :loginVisible.sync="loginVisible"></reset-password>

  </div>
</template>

<script>
import Login from '@/components/login'
import Registry from '@/components/registry'
import ChangePassword from '@/components/ChangePassword'
import ResetPassword from '@/components/ResetPassword'
import { MessageBox, Message } from 'element-ui'
// import qs from 'qs'
export default {
  name: 'Navigation',
  components: {
    Login,
    Registry,
    ChangePassword,
    ResetPassword
  },
  data () {
    return {
      author: 'fadedfat3',
      title: '编程技术博客',
      description: '学习技术，分享经验，提升能力，快乐生活',
      user: this.$store.getters.user,
      loginVisible: false,
      registryVisible: false,
      changePasswordVisible: false,
      resetPasswordVisible: false
    }
  },
  computed: {
    userProfile: function () {
      return '/user/profile'
    },
    userPassword: function () {
      return '/user/changePassword'
    },
    articleIndex () {
      return '/article/index/' + this.user.id
    },
    login () {
      return !!this.$store.getters.token
    },
    username () {
      return this.$store.getters.user.username
    },
    timeout () {
      return this.$store.getters.timeout
    },
    isNeededLogin () {
      return this.$store.getters.isNeededLogin
    },
    avatar () {
      return this.user.avatar
    },
    adminHome () {
      return '/article/list'
    }
  },
  watch: {
    timeout (val) {
      if (val === true) {
        MessageBox.confirm('用户认证令牌失效，需要重新登陆', '重新登陆', {
          confirmButtonText: '登陆',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loginVisible = true
        })
      }
    },
    isNeededLogin (val) {
      if (val === true) {
        this.loginVisible = true
      }
    }
  },
  methods: {
    logout () {
      this.$store.dispatch('user/logout').then(res => {
        Message.success(res.message)
      })
      this.loginVisible = false
    },
    changePassword () {
      this.changePasswordVisible = true
    },
    showLoginDialog () {
      this.loginVisible = true
    }
  }
}
</script>

<style scoped>
.nav-title {
  text-align: left;
  margin: 5px;
}
.nav-description {
  text-align: left;
  margin: 10px;
}
.nav-login {
  float: right;
}
.nav-user {
  float: right;
}
</style>
