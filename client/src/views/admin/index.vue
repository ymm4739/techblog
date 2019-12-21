<template>
  <el-row>
    <el-col :span="4">
      <blog-nav-menu />
    </el-col>
    <el-col :span="20">
      <router-view />
    </el-col>
  </el-row>
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
