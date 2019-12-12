<template>
  <div>
    <h3 class="nav-title">{{title}}</h3>
    <h4 class="nav-description">{{description}}</h4>
    <el-menu :default-active="$route.path"
             mode="horizontal"
             background-color="black"
             text-color="white"
             router
             active-text-color="yellow">
      <el-menu-item index="/home">首页</el-menu-item>
      <el-menu-item index="/post">文章</el-menu-item>
      <el-menu-item index="/book">书籍</el-menu-item>
      <el-submenu index="/tutorial">
        <template slot="title">教程</template>
        <el-menu-item index="/java">Java</el-menu-item>
        <el-menu-item index="/vue">Vue</el-menu-item>
        <el-menu-item index="/python">Python</el-menu-item>
      </el-submenu>
      <el-menu-item index="/talk">杂谈</el-menu-item>
      <el-menu-item index=""
                    v-if="!login"
                    class="nav-login"
                    @click="showLoginDialog">登陆</el-menu-item>
      <el-submenu v-if="login"
                  class="nav-user"
                  index="-1">
        <template slot="title">
          <el-avatar>{{ username }}</el-avatar>
        </template>
        <el-menu-item index="/user/profile">个人资料</el-menu-item>
        <el-menu-item index=""
                      @click="logout">退出登陆</el-menu-item>
      </el-submenu>
    </el-menu>

    <Login :show.sync="loginVisible"
           @close-login-dialog="closeLoginDialog"
           @show-registry-dialog="showRegistryDialog"></Login>
    <Registry :visible="registryVisible"
              @close-login-dialog="closeLoginDialog"
              @show-login-dialog="showLoginDialog"
              @close-registry-dialog="closeRegistryDialog"></Registry>
  </div>
</template>

<script>
import Login from '@/components/login'
import Registry from '@/components/registry'
import { MessageBox, Message } from 'element-ui'
export default {
  name: 'Navigation',
  components: {
    Login,
    Registry
  },
  data () {
    return {
      author: 'fadedfat3',
      title: '编程技术博客',
      description: '学习技术，分享经验，提升能力，快乐生活',
      user: this.$store.getters.user,
      loginVisible: false,
      registryVisible: false
    }
  },
  computed: {
    userPath: function () {
      return { name: 'profile' }
    },
    userPassword: function () {
      return { path: '/user/changePassword' }
    },
    login () {
      return !!this.$store.getters.token
    },
    username () {
      return this.$store.getters.user.username
    },
    timeout () {
      return this.$store.getters.timeout
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
    }
  },
  methods: {
    logout () {
      this.$store.dispatch('user/logout').then(res => {
        Message.success(res.message)
      })
      this.loginVisible = false
    },
    showLoginDialog () {
      this.loginVisible = true
    },
    closeLoginDialog () {
      this.loginVisible = false
    },
    closeRegistryDialog () {
      this.registryVisible = false
    },
    showRegistryDialog () {
      this.registryVisible = true
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
