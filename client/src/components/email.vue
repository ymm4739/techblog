<template>
  <div>
    <h2>激活邮箱</h2>
    <p v-if="success">{{successMessage}}</p>
    <p v-if="!success">{{errorMessage}}</p>
  </div>
</template>
<script>
export default {
  name: 'ActivateEmail',
  data () {
    return {
      successMessage: '',
      errorMessage: '',
      success: !!this.$store.state.user.isValidEmail
    }
  },
  created () {
    let data = {
      token: this.$route.query.token
    }
    this.$store.dispatch('user/activateEmail', data).then(res => {
      this.successMessage = res.message
      this.success = !!this.$store.state.user.isValidEmail
    }).catch(error => {
      this.errorMessage = error.message
    })
  }
}
</script>
