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
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   @click="update">修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { getInfo } from '@/api/user'
export default {
  name: 'Profile',
  data () {
    return {
      user: this.$store.getters.user,
      modify: false
    }
  },
  created () {
    if (this.username === '') {
      getInfo().then(res => {
        this.user = res.data
        this.$store.dispatch('/user/setUserInfo', this.user)
      })
    }
  },
  computed: {
    username () {
      return this.user.username
    },
    email () {
      return this.user.email
    }

  },
  methods: {
    update () {
      this.modify = true
    }
  }
}
</script>
<style scoped>
.profile {
  top: 10%;
}
</style>
