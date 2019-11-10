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
      <el-menu-item index="/">首页</el-menu-item>
      <el-menu-item index="/post">文章</el-menu-item>
      <el-menu-item index="/book">书籍</el-menu-item>
      <el-submenu index="4">
        <template slot="title">教程</template>
        <el-menu-item index="4-1">Java</el-menu-item>
        <el-menu-item index="4-2">Vue</el-menu-item>
        <el-menu-item index="4-2">Python</el-menu-item>
      </el-submenu>
      <el-menu-item index="/hello">杂谈</el-menu-item>
      <el-menu-item index="/login"
                    v-if="!login"
                    class="nav-login">登陆</el-menu-item>
      <el-submenu v-if="login"
                  class="nav-user">
        <template slot="title">{{ user.name }}</template>
        <el-menu-item :index="userPath">个人资料</el-menu-item>
        <el-menu-item :index="userPassword">修改密码</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'navigation',
  components: {},
  data () {
    return {
      author: 'fadedfat3',
      title: '编程技术博客',
      description: '学习技术，分享经验，提升能力，快乐生活',
      activeIndex: '/',
      login: false,
      user: {
        name: 'admin',
        id: 1
      }
    }
  },
  computed: {
    userPath: function () {
      return '/user/' + this.user.id
    },
    userPassword: function () {
      return '/user/changePassword' + this.user.id
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
