<template>
  <el-container class="container">
    <el-header>
      <Navigation />
    </el-header>
    <el-container>
      <router-view />
    </el-container>
  </el-container>
</template>
<script>
import Navigation from './components/navigation'
export default {
  name: 'Layout',
  components: {
    Navigation
  }
}
</script>
<style scoped>
.container {
  background-color: #f4f5f5;
}
</style>
