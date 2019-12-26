<template>
  <el-container>
    <el-aside style="">
      <admin-nav-menu />
    </el-aside>
    <el-container>
      <router-view />

    </el-container>
  </el-container>
</template>
<script>
import AdminNavMenu from '@/components/AdminNavMenu'
export default {
  name: 'AdminLayout',
  components: {
    AdminNavMenu
  }
}
</script>
