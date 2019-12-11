<template>
  <div>
    <h3 class="nav-title">{{title}}</h3>
    <h4 class="nav-description">{{description}}</h4>
    <el-menu :default-active="activeIndex"
             mode="horizontal"
             background-color="black"
             text-color="white"
             active-text-color="yellow"
             router>
      <el-menu-item index="/home">首页</el-menu-item>
      <el-menu-item index="/post">文章</el-menu-item>
      <el-menu-item index="/book">书籍</el-menu-item>
      <el-submenu index="/tutors">
        <template slot="title">教程</template>
        <el-menu-item index="/java">Java</el-menu-item>
        <el-menu-item index="/vue">Vue</el-menu-item>
        <el-menu-item index="/python">Python</el-menu-item>
      </el-submenu>
      <el-menu-item index="/talk">杂谈</el-menu-item>
      <el-menu-item index="/login"
                    v-if="!login"
                    class="nav-login">登陆</el-menu-item>
      <el-submenu v-if="login"
                  class="nav-user">
        <template slot="title">{{ user.username }}</template>
        <el-menu-item :index="userPath">个人资料</el-menu-item>
        <el-menu-item :index="userPassword">修改密码</el-menu-item>
        <el-menu-item @click="logout">退出登陆</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'Navigation',
  components: {},
  data () {
    return {
      author: 'fadedfat3',
      title: '编程技术博客',
      description: '学习技术，分享经验，提升能力，快乐生活',
      activeIndex: '/',
      user: this.$store.getters.user
    }
  },
  computed: {
    userPath: function () {
      return '/user/profile'
    },
    userPassword: function () {
      return '/user/changePassword'
    },
    login () {
      return !!this.$store.getters.token
    }
  },
  watch: {

  },
  methods: {
    logout () {
      this.$store.dispatch('user/logout')
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
