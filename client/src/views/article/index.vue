<template>
  <el-main class="main">
    <div>
      <h2 v-if="!articles.length">暂无博客</h2>
      <ul style="margin:0px;padding:0px">
        <article-index-item v-for="article in articles"
                            :article="article"
                            :authorID="authorID"
                            :isAuthor="true"
                            :likes="likes"
                            :collections="collections"
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
      authorID: this.$route.params.authorID,
      readerID: this.$store.getters.userID,
      likes: [],
      collections: []
    }
  },
  created () {
    index(this.authorID, this.readerID).then(res => {
      let data = res.data
      if (data) {
        this.articles = data.articles
        this.likes = data.likes
        this.collections = data.collections
      }
    })
  },
  computed: {

  }
}
</script>
<style scoped>
.main {
  background-color: white;
}
</style>
