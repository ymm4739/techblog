<template>
  <div>
    <ul>
      <article-index-item v-for="article in articles"
                          :article="article"
                          :userID="userID"
                          :key="article.id"></article-index-item>
    </ul>
  </div>
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
      userID: this.$route.params.userID
    }
  },
  created () {
    let userID = this.$route.params.userID
    index(userID).then(res => {
      this.articles = res.data
    })
  }
}
</script>
