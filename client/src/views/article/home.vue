<template>
  <el-main class="main">
    <h2 v-if="!articles.length && noMore">暂无博客</h2>
    <article-index-item class="item"
                        v-for="article in articles"
                        :article="article"
                        :authorID="authorID"
                        :likes="likes"
                        :collections="collections"
                        :key="article.id"></article-index-item>
    <p v-if="loading"
       v-loading="loading"
       element-loading-text="拼命加载中"
       element-loading-spinner="el-icon-loading"></p>
    <p v-if="noMore">没有更多了</p>
  </el-main>
</template>
<script>
import ArticleIndexItem from './components/ArticleIndexItem'
import { getHomeArticles } from '@/api/article'
import InfiniteLoading from 'vue-infinite-loading'
export default {
  name: 'ArticleIndexView',
  components: {
    ArticleIndexItem,
    InfiniteLoading
  },
  data () {
    return {
      articles: [],
      authorID: this.$route.params.authorID,
      readerID: this.$store.getters.userID,
      likes: [],
      collections: [],
      loading: false,
      noMore: false,
      limit: 5,
      offset: 0
    }
  },
  created () {
    this.fetchData()
  },
  mounted () {
    this.scroll()
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
      setTimeout(() => {
        getHomeArticles(query).then(res => {
          let data = res.data
          if (data) {
            let result = data.articles
            this.likes = data.likes
            this.collections = data.collections
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
    },
    scroll () {
      window.onscroll = () => {
        let bottomOfWindow = document.documentElement.offsetHeight - document.documentElement.scrollTop - window.innerHeight <= 200
        if (bottomOfWindow && !this.scrollDisable) {
          this.fetchData()
        }
      }
    }
  }
}
</script>
<style scoped>
.main {
  background-color: white;
  margin-bottom: 0px;
}

.item {
  max-height: 300px;
  padding: 10px;
  margin-top: 0;
}
.item:hover {
  background-color: #f4f5f5;
}
</style>
