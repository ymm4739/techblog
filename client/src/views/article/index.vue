<template>
  <el-main class="main">
    <div>
      <h2 v-if="!articles.length">暂无博客</h2>
      <ul style="margin:0px;padding:0px">
        <article-index-item v-for="article in articles"
                            :article="article"
                            :authorID="authorID"
                            :likes="likes"
                            :key="article.id"></article-index-item>
      </ul>
    </div>
  </el-main>
</template>
<script>
import ArticleIndexItem from './components/ArticleIndexItem'
import { index } from '@/api/article'
export default {
  name: 'ArticleIndexView',
  components: {
    ArticleIndexItem
  },
  data () {
    return {
      articles: [],
      authorID: this.$route.params.userID,
      readerID: this.$store.getters.userID,
      likes: []
    }
  },
  created () {
    let authorID = this.$route.params.userID
    let readerID = this.$store.getters.userID
    console.log(readerID)
    index(authorID, readerID).then(res => {
      let data = res.data
      console.log(data)
      if (data) {
        this.articles = data.articles
        this.likes = data.likes
      }
    })
  }
}
</script>
<style scoped>
.main {
  background-color: white;
}
</style>
