<template>
  <div>
    <h3>{{article.title}}</h3>
    <div v-html="article.html"></div>
    <el-divider></el-divider>
    <span class="info">Posted At {{article.createdTime}} By <el-tag>{{author.username}}</el-tag></span>
  </div>
</template>
<script>
import { show } from '@/api/article'
export default {
  name: 'ArticleShowView',
  data () {
    return {
      article: '',
      author: ''
    }
  },
  created () {
    let params = this.$route.params.articleID
    show(params).then(res => {
      this.article = res.data
      this.author = this.article.author
    }).catch(() => { })
  }
}
</script>
<style scoped>
.info {
  float: right;
}
</style>
