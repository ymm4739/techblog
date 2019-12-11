<template>
  <div class="profile">
    <el-form>
      <el-form-item label="用户名">{{user.username}}
        <el-input v-if="modify"
                  :placeholder="username">
        </el-input>
      </el-form-item>
      <el-form-item label="邮箱">{{user.email}}
        <el-input v-if="modify"
                  :placeholder="email"></el-input>
        <el-button v-if="!activated"
                   type="primary"
                   @click="activate">激活邮箱</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   @click="update">修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  name: 'Profile',
  data () {
    return {
      user: this.$store.getters.user,
      modify: false,
      activated: this.$store.state.user.isValidEmail
    }
  },
  created () {
    if (this.username === '') {
      this.$store.dispatch('user/getInfo')
    }
  },
  computed: {
    username () {
      return this.$store.state.user.username
    },
    email () {
      return this.$store.state.user.email
    }

  },
  methods: {
    update () {
      this.modify = true
    },
    activate () {
      let data = {
        email: this.email
      }
      this.$store.dispatch('user/sendActivatedEmail', data)
    }
  }
}
</script>
<style scoped>
.profile {
  top: 10%;
}
</style>
