<template>
  <div>
    <h2>Username: {{user.username}}</h2>
    <h2>Email: {{user.email}}</h2>

  </div>
</template>
<script>
import { getInfo } from '@/api/user'
export default {
  name: 'UserInfo',
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
      if (this.$store.getters['user/token'] === '') {
        getInfo().then(res => {
          this.user = res.data
          this.$store.dispatch('user/setUserInfo', this.user)
        })
      } else {
        this.user = this.$store.getters['user/user']
      }
    }
  }
}
</script>
