<template>
  <div>11111</div>
</template>

<script>
import request from '@/utils/request'
export default {
  name: 'Home',
  components: {},
  data () {
    return {
      author: 'fadedfat3',
      title: '编程技术博客',
      description: '学习技术，分享经验，提升能力，快乐生活',
      activeIndex: '/'
    }
  },
  created () {
    request.get('/user/list').then(res => {
      console.log(res.data)
    })
  }
}
</script>

<style scoped>
</style>
