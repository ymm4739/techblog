<template>
  <div class='article_list'>
    <el-row>
      <span class="desc_left">文章列表</span>
      <el-button class="create_button"
                 @click="create">新建</el-button>
    </el-row>
    <el-row>
      <el-col :span="3">
        <el-input v-model="search"
                  placeholder="关键字搜索"></el-input>
      </el-col>
    </el-row>

    <el-table :data="articles.filter(data => !search || data.title.toLowerCase().includes(search.toLowerCase().trim()))"
              v-if="articles.length"
              stripe
              highlight-current-row
              size="mini"
              :default-sort="{prop: 'updatedTime', order: 'descending'}">
      <el-table-column prop="title"
                       label="标题"
                       min-width="300px"></el-table-column>
      <el-table-column prop="updatedTime"
                       label="时间"
                       :sortable="true"
                       width="200px"></el-table-column>
      <el-table-column label="状态"
                       width="80px"
                       prop="isPublished"
                       :sortable="true"
                       :formatter="publishedFormatter"
                       :filters="[{text:'已发布', value: 1}, {text:'草稿', value: 0}]"
                       :filter-method="publishedFilter">

      </el-table-column>
      <el-table-column prop="likedNums"
                       label="点赞数"
                       :sortable="true"
                       width="100px"></el-table-column>
      <el-table-column prop="commentNums"
                       label="评论数"
                       :sortable="true"
                       width="100px"></el-table-column>
      <el-table-column prop="collectedNums"
                       label="收藏数"
                       :sortable="true"
                       width="100px"></el-table-column>
      <el-table-column label="操作"
                       width="250px">
        <template slot-scope="scope">
          <el-button size="mini"
                     @click="handlerView(scope.row)">查看</el-button>
          <el-button size="mini"
                     @click="handlerUpdate(scope.row)">编辑</el-button>
          <el-button size="mini"
                     type="danger"
                     @click="handlerDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-if="!articles.length">暂无数据</div>
  </div>
</template>
<script>
import { list, _delete } from '@/api/article'
import { Message } from 'element-ui'
export default {
  name: 'ArticleListView',
  data () {
    return {
      articles: [],
      urlPrefix: '/user/' + this.$route.params.userID + '/article/',
      userID: this.$route.params.userID,
      search: ''
    }
  },
  created () {
    list(this.userID).then(res => {
      this.articles = res.data
    })
  },
  inject: [
    'reload'
  ],
  methods: {
    handlerView (row) {
      if (row.isPublished) {
        this.$router.push({ path: this.urlPrefix + 'show/' + row.id })
      } else {
        this.$router.push({ path: this.urlPrefix + 'draft/' + row.id })
      }
    },
    create () {
      this.$router.push({ path: this.urlPrefix + 'create' })
    },
    handlerUpdate (row) {
      this.$router.push({ path: this.urlPrefix + 'edit/' + row.id })
    },
    handlerDelete (row) {
      if (confirm('确定删除吗？')) {
        _delete(this.userID, row.id).then(res => {
          Message.success(res.message)
          this.reload()
        })
      }
    },
    publishedFormatter (row, column) {
      return row.isPublished ? '已发布' : '草稿'
    },
    publishedFilter (value, row) {
      return value === row.isPublished
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
