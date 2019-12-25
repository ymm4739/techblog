<template>
  <el-container>
    <el-aside style="">
      <blog-nav-menu />
    </el-aside>
    <el-container>
      <router-view />

    </el-container>
  </el-container>
</template>
<script>
import BlogNavMenu from '@/components/BlogNavMenu'
export default {
  name: 'AdminLayout',
  components: {
    BlogNavMenu
  }
}
</script>
