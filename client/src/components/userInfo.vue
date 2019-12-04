<template>
  <div>
    <h2>Username: {{user.username}}</h2>
    <h2>Email: {{user.email}}</h2>
    <h2>Password: {{user.password}}</h2>
    <h2>Token: {{user.token}}</h2>
  </div>
</template>
<script>
import request from '@/utils/request'
export default {
  name: 'userInfo',
  data () {
    return {
      'user': ''
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      if (this.$store.getters.token === '') {
        request.get('/user/info').then(res => {
          this.user = res.data
          this.$store.dispatch('setUserInfo', this.user)
        })
      } else {
        this.user = this.$store.getters.user
      }
    }
  }
}
</script>
