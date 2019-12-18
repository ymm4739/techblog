<template>
  <div>
    <div><span>你创造的所有文章</span>
      <el-button class="create_button"
                 @click="create">新建</el-button>
    </div>
    <el-table :data="articles"
              v-if="articles.length">
      <el-table-column prop="title"
                       label="标题"></el-table-column>
      <el-table-column prop="updatedTime"
                       label="时间"></el-table-column>
      <el-table-column prop="liked_nums"
                       label="点赞数"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click="handlerView(scope.row)">查看</el-button>
          <el-button>删除</el-button>
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
      console.log('articles:')
      console.log(this.articles)
    })
  },
  methods: {
    handlerView (row) {
      this.$router.push({ path: '/article/show/' + row.id })
    },
    create () {
      this.$router.push({ path: '/article/edit' })
    }
  }
}
</script>
<style scoped>
.create_button {
  float: right;
}
</style>
