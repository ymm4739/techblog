<template>
  <div>
    <div><span class="desc_left">文章列表</span>
      <el-button class="create_button"
                 @click="create">新建</el-button>
    </div>
    <el-table :data="articles"
              v-if="articles.length"
              size="mini">
      <el-table-column prop="title"
                       label="标题"></el-table-column>
      <el-table-column prop="updatedTime"
                       label="时间"></el-table-column>
      <el-table-column prop="likedNums"
                       label="点赞数"></el-table-column>
      <el-table-column prop="commentNums"
                       label="评论数"></el-table-column>
      <el-table-column prop="collectedNums"
                       label="收藏数"></el-table-column>
      <el-table-column label="操作">
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
      articles: []
    }
  },
  created () {
    list().then(res => {
      this.articles = res.data
    })
  },
  methods: {
    handlerView (row) {
      this.$router.push({ path: '/article/show/' + row.id })
    },
    create () {
      this.$router.push({ path: '/article/create' })
    },
    handlerUpdate (row) {
      this.$router.push({ path: '/article/edit/' + row.id })
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
</style>
