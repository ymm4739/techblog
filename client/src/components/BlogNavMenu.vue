<template>
  <div>
    <el-menu router
             active-text-color="blue"
             :default-active="$route.path">
      <el-menu-item :index="blogIndex">博客首页</el-menu-item>
      <el-menu-item :index="articleCreate">新文章</el-menu-item>
      <el-submenu index="-1">
        <template slot="title">
          管理
        </template>
        <el-menu-item :index="articleAdmin">文章</el-menu-item>
        <el-menu-item :index="catelogAdmin">分类</el-menu-item>
        <el-menu-item :index="commentAdmin">评论</el-menu-item>
        <el-menu-item :index="fileAdmin">文件</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>

</template>
<script>
export default {
  name: 'BlogNavMenu',
  data () {
    return {
      userID: this.$store.getters.user.id
    }
  },
  computed: {
    blogIndex () {
      return '/user/' + this.userID + '/article/index'
    },
    articleCreate () {
      return '/user/' + this.userID + '/article/create'
    },
    catelogAdmin () {
      return '/user/' + this.userID + '/catelog/list'
    },
    articleAdmin () {
      return '/user/' + this.userID + '/article/list'
    },
    fileAdmin () {
      return '/user/' + this.userID + '/file/list'
    },
    commentAdmin () {
      return '/user/' + this.userID + '/comment/list'
    }
  }
}
</script>
