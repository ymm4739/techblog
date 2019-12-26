<template>
  <el-main>
    <div>
      <el-menu router
               active-text-color="blue"
               :default-active="$route.path">
        <el-submenu index="1"
                    :popper-append-to-body="true"
                    popper-class="submenu">
          <template slot="title">
            内容管理
          </template>
          <el-menu-item :index="articleAdmin">文章</el-menu-item>
          <el-menu-item :index="catelogAdmin">分类</el-menu-item>
          <el-menu-item :index="commentAdmin">评论</el-menu-item>
          <el-menu-item :index="collectionAdmin">收藏</el-menu-item>
        </el-submenu>
        <el-submenu index="2"><template slot="title">消息管理</template>
          <el-menu-item index="msg"></el-menu-item>
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
    catelogAdmin () {
      return '/catelog/list'
    },
    articleAdmin () {
      return '/article/list'
    },
    collectionAdmin () {
      return '/collection/list'
    },
    commentAdmin () {
      return '/comment/list'
    }
  }
}
</script>
