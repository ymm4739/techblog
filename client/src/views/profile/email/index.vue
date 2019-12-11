<template>
  <div>
    <h2>激活邮箱</h2>
    <p v-if="success">邮箱激活成功，你现在可以用邮箱重置密码</p>
    <p v-if="!success">邮箱激活失败，{{message}}</p>
  </div>
</template>
<script>
export default {
  name: 'ActivateEmail',
  data () {
    return {
      message: '',
      success: !!this.$store.state.user.isValidEmail
    }
  },
  created () {
    let data = {
      token: this.$route.query.token
    }
    this.$store.dispatch('user/activateEmail', data).then(res => {
      this.message = res.message
    })
  }
}
</script>
