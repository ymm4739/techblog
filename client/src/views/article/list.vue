<template>
  <div class='article_list'>
    <div>
      <span class="desc_left">文章列表</span>
      <el-button class="create_button"
                 @click="create">新建</el-button>
    </div>
    <el-table :data="articles"
              v-if="articles.length"
              stripe
              highlight-current-row
              size="mini">
      <el-table-column prop="title"
                       label="标题"
                       min-width="400px"></el-table-column>
      <el-table-column prop="updatedTime"
                       label="时间"
                       width="200px"></el-table-column>
      <el-table-column prop="likedNums"
                       label="点赞数"
                       width="100px"></el-table-column>
      <el-table-column prop="commentNums"
                       label="评论数"
                       width="100px"></el-table-column>
      <el-table-column prop="collectedNums"
                       label="收藏数"
                       width="100px"></el-table-column>
      <el-table-column label="操作"
                       width="250px">
        <template slot-scope="scope">
          <el-button size="mini"
                     @click="handlerView(scope.row)">查看</el-button>
          <el-button size="mini"
                     @click="handlerUpdate(scope.row)">编辑</el-button>
          <el-button size="mini">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-if="!articles.length">暂无数据</div>
  </div>
</template>
<script>
import { list } from '@/api/article'
export default {
  name: 'ArticleListView',
  data () {
    return {
      articles: [],
      urlPrefix: '/user/' + this.$route.params.userID + '/article/',
      userID: this.$route.params.userID
    }
  },
  created () {
    list(this.userID).then(res => {
      this.articles = res.data
    })
  },
  methods: {
    handlerView (row) {
      this.$router.push({ path: this.urlPrefix + 'show/' + row.id })
    },
    create () {
      this.$router.push({ path: this.urlPrefix + 'create' })
    },
    handlerUpdate (row) {
      this.$router.push({ path: this.urlPrefix + 'edit/' + row.id })
    }
  }
}
</script>
<style scoped>
.create_button {
  float: right;
}
.desc_left {
  float: left;
}
.article_list {
  margin-left: 20px;
  margin-top: 20px;
}
</style>
