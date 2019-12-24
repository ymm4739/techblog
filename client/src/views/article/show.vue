<template>
  <div class="main">
    <h2 v-if="!article">暂无已发布的文章</h2>
    <div v-if="article">
      <h3>{{article.title}}</h3>
      <div v-html="article.html"></div>

      <el-divider></el-divider>
    </div>
    <div class="info">{{article.createdTime}} <i class="el-icon-chat-dot-square">{{article.commentNums}}</i><i class="el-icon-collection-tag">{{article.collectedNums}}</i> </div>

    <div>
      <el-image :src="author.avatar"
                style="width:100px;height:100px"
                fit="cover"></el-image>
      <el-link icon="el-icon-chat-dot-square"
               :underline="false">评论</el-link>
      <el-link icon="el-icon-collection-tag"
               :underline="false">收藏</el-link>
    </div>
  </div>
</template>
<script>
import { show, edit } from '@/api/article'
export default {
  name: 'ArticleShowView',
  data () {
    return {
      article: '',
      author: ''
    }
  },
  created () {
    let id = this.$route.params.articleID
    let userID = this.$route.params.userID
    let path = this.$route.path
    let methodName = path.split('/')[2]
    if (methodName === 'show') {
      show(userID, id).then(res => {
        if (res.data) {
          this.article = res.data
          this.author = this.article.author
        }
      }).catch(() => { })
    } else {
      let articleID = id
      edit(userID, articleID).then(res => {
        if (res.data) {
          this.article = res.data
          this.author = this.article.author
        }
      })
    }
  }
}
</script>
<style scoped>
.info {
  float: right;
}
.main {
  margin-left: 20px;
  margin-top: 30px;
}
</style>
