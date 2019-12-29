<template>
  <el-main>
    <div class='article_list'
         v-loading="loading"
         element-loading-text="拼命加载中"
         element-loading-spinner="el-icon-loading"
         element-loading-background="white">
      <el-row>
        <span class="desc_left">文章列表</span>
        <el-button class="create_button"
                   @click="create">新建</el-button>
      </el-row>
      <el-row>
        <el-col :span="4">
          <el-input v-model="search"
                    placeholder="关键字搜索"
                    clearable
                    @clear="handlerSearchClear">
            <el-button slot="append"
                       size="mini"
                       @click="handlerSearch">
              <font-awesome-icon :icon="['fas', 'search']" />
            </el-button>
          </el-input>

        </el-col>
      </el-row>

      <el-table :data="tableData"
                v-if="articles.length"
                size="mini"
                @sort-change="customSort"
                :row-class-name="tableStyle">
        <el-table-column prop="title"
                         label="标题"
                         min-width="250px">
          <template slot-scope="scope">
            <el-button type="text"
                       size="mini"
                       @click="handlerView(scope.row)">{{scope.row.title}}</el-button>
          </template></el-table-column>

        <el-table-column prop="updatedTime"
                         label="时间"
                         sortable="custom"
                         width="200px"></el-table-column>
        <el-table-column label="状态"
                         width="80px"
                         prop="isPublished"
                         sortable="custom"
                         :formatter="publishedFormatter"
                         :filters="[{text:'已发布', value: 1}, {text:'草稿', value: 0}]"
                         :filter-method="publishedFilter">

        </el-table-column>
        <el-table-column prop="likedNums"
                         label="点赞数"
                         sortable="custom"
                         width="100px"></el-table-column>
        <el-table-column prop="commentNums"
                         label="评论数"
                         sortable="custom"
                         width="100px"></el-table-column>
        <el-table-column prop="collectedNums"
                         label="收藏数"
                         sortable="custom"
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
      <el-pagination :hide-on-single-page="false"
                     layout="total, sizes, prev, pager, next, jumper"
                     :page-size="pageSize"
                     :page-sizes="pageSizes"
                     :total="total"
                     :current-page="currentPage"
                     @size-change="handlerSizeChange"
                     @current-change="handlerCurrentChange"
                     class="pagination"></el-pagination>

      <div v-if="!articles.length">暂无数据</div>
    </div>
  </el-main>
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
      userID: this.$store.getters.userID,
      search: '',
      loading: true,
      pageSizes: [5, 10, 20],
      pageSize: 5,
      currentPage: 1,
      sort: 'updated_time',
      order: 'desc',
      total: 0
    }
  },
  created () {
    this.fetchData()
  },
  computed: {
    filterData () {
      return this.articles.filter(data => !this.search || data.title.toLowerCase().includes(this.search.toLowerCase().trim()))
    },
    tableData () {
      // let data = this.filterData
      // return data.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
      return this.articles
    },
    offset () {
      return (this.currentPage - 1) * this.pageSize
    }
  },
  inject: [
    'reload'
  ],
  methods: {
    fetchData () {
      this.loading = true
      let data = {
        authorID: this.userID,
        offset: this.offset,
        limit: this.pageSize,
        sort: this.sort,
        order: this.order,
        search: this.search
      }
      list(data).then(res => {
        this.total = res.data.total || 0
        this.articles = res.data.data || []
      })
      this.loading = false
    },
    handlerSearch () {
      this.currentPage = 1
      this.fetchData()
    },
    handlerSizeChange (val) {
      this.pageSize = val
      this.handlerCurrentChange(1)
    },
    handlerCurrentChange (val) {
      this.currentPage = val
      this.fetchData()
    },
    handlerView (row) {
      if (row.isPublished) {
        this.$router.push({ path: '/article/show/' + row.id })
      } else {
        this.$router.push({ path: '/article/draft/' + row.id })
      }
    },
    create () {
      this.$router.push({ path: '/article/create' })
    },
    handlerUpdate (row) {
      this.$router.push({ path: '/article/edit/' + row.id })
    },
    handlerDelete (row) {
      if (confirm('确定删除吗？')) {
        _delete(row.id).then(res => {
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
    },
    customSort (obj) {
      console.log(obj)
      let { prop, order } = obj
      console.log(prop)
      console.log(order)
      this.order = order === 'ascending' ? 'asc' : 'desc'
      if (prop === 'updatedTime') {
        this.sort = 'updated_time'
      } else if (prop === 'isPublished') {
        this.sort = 'is_published'
      } else if (prop === 'likedNums') {
        this.sort = 'liked_nums'
      } else if (prop === 'commentNums') {
        this.sort = 'comment_nums'
      } else if (prop === 'collectNums') {
        this.sort = 'collect_nums'
      }
      this.handlerCurrentChange(1)
    },
    tableStyle ({ row, rowIndex }) {
      if (row.isPublished === 1) {
        return 'published_row'
      }
      return ''
    },
    handlerSearchClear () {
      this.search = ''
      this.handlerCurrentChange(1)
    }
  }
}
</script>
<style >
.create_button {
  float: right;
}
.desc_left {
  float: left;
}

.pagination {
  text-align: center;
  margin-top: 20px;
  margin-bottom: 20px;
}
.el-table .published_row {
  font-weight: bolder;
}
.el-table .draft_row {
  background: greenyellow;
}
</style>
