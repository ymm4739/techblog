<template>
  <el-main style="width:240px">
    <div>
      <el-menu router
               active-text-color="blue"
               :default-active="$route.path">
        <el-submenu index="1"
                    :popper-append-to-body="true"
                    popper-class="submenu"
                    style="width:200px">
          <template slot="title">
            内容管理
          </template>
          <el-menu-item :index="articleAdmin">文章</el-menu-item>
          <el-menu-item :index="thumbsAdmin">点赞</el-menu-item>
          <el-menu-item :index="commentAdmin">评论</el-menu-item>
          <el-menu-item :index="collectionAdmin">收藏</el-menu-item>
        </el-submenu>
        <el-submenu index="2"
                    style="width:200px">
          <template slot="title">消息管理</template>
        </el-submenu>

      </el-menu>
    </div>
  </el-main>
</template>
<script>
export default {
  name: 'AdminNavMenu',
  data () {
    return {
      userID: this.$store.getters.user.id,
      submenu: {
        width: '150px'
      }
    }
  },
  computed: {
    articleAdmin () {
      return '/article/list'
    },
    collectionAdmin () {
      return '/collection/list'
    },
    commentAdmin () {
      return '/comment/list'
    },
    thumbsAdmin () {
      return '/article/thumbs/list'
    }
  }
}
</script>
