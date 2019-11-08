<template>
  <div class="login">
    <el-form :model="form" label-position="left" label-width="60px" :rules="rules" status-icon >
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入电子邮箱" clearable></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" placeholder="请输入密码" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="form.isRememberMe" style="float:left">记住我</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">登陆</el-button>
      </el-form-item>
      <el-form-item ><el-button type="primary" @click="signup">注册</el-button><el-button type="primary" @click="resetPassword">忘记密码</el-button></el-form-item>
    </el-form>
  </div>
</template>
<script>
import service from '@/utils/require'
export default {
  data() {
    return {
      form: {
        email: "",
        password: "",
        isRememberMe: false
      },
      rules: {
        email: [
          {
            require: true,
            type: "email",
            message: "请输入正确格式邮箱地址",
            trigger: "blur"
          }
        ],
        password: [
          { require: true, message: "请输入密码", trigger: "blur" },
          { min: 6, message: "密码长度为6位及以上", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit: function() {
      console.log(this.form);
      service.post('/login', {
        'username': this.form.email,
        'password': this.form.password,
        'isRememberMe': this.form.isRememberMe
      })
      .then()
    }
  }
};
</script>
<style scoped>
.login {
  margin: 300px;
}
</style>