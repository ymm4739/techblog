<template>
  <el-main class="main">
    <div class="infinite">
      <h2 v-if="!articles.length">暂无博客</h2>
      <ul style="margin:0px;padding:0px;"
          v-infinite-scroll="fetchData"
          infinite-scroll-disabled="scrollDisable">
        <article-index-item v-for="article in articles"
                            :article="article"
                            :authorID="authorID"
                            :likes="likes"
                            :key="article.id"></article-index-item>
      </ul>
      <p v-if="loading"
         v-loading="loading"
         element-loading-text="拼命加载中"
         element-loading-spinner="el-icon-loading"></p>
      <p v-if="noMore">没有更多了</p>
    </div>
  </el-main>
</template>
<script>
import ArticleIndexItem from './components/ArticleIndexItem'
import { getHomeArticles } from '@/api/article'
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
      loading: false,
      noMore: false,
      limit: 10,
      offset: 0
    }
  },
  created () {
    // this.fetchData()
  },
  computed: {
    scrollDisable () {
      return this.noMore || this.loading
    }
  },
  methods: {
    fetchData () {
      this.loading = true
      let query = {
        readerID: this.readerID,
        offset: this.offset,
        limit: this.limit
      }
      console.log(this.noMore)
      console.log(query)
      setTimeout(() => {
        getHomeArticles(query).then(res => {
          let data = res.data
          if (data) {
            let result = data.articles
            this.likes = data.likes
            if (result && result.length > 0) {
              this.articles = this.articles.concat(result)
              this.offset += result.length
            } else {
              this.noMore = true
            }
          }
          this.loading = false
        }).catch(() => {
        })
      }, 500)
    }
  }
}
</script>
<style scoped>
.main {
  background-color: white;
}
.infinite {
  overflow: auto;
  height: 1000px;
}
</style>
