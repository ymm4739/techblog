<template>
  <div>
    <el-button type="text"
               @click="view(article.id)">{{article.title}}</el-button>
    <div>摘要：<span>{{article.summary}}</span></div>
    <div class="desc_right">发布于 {{article.updatedTime}} 评论({{article.commentNums}})</div>
    <el-divider></el-divider>
  </div>
</template>
<script>
import VClamp from 'vue-clamp'
export default {
  name: 'ArticleIndexItem',
  components: {
    VClamp
  },
  props: {
    article: {
      type: Object,
      default: null
    },
    userID: {
      type: String,
      default: ''
    }
  },
  methods: {
    view (id) {
      this.$router.push({ path: '/user/' + this.userID + '/article/show/' + id })
    }
  }
}
</script>
<style scoped>
.desc_right {
  float: right;
}
</style>
