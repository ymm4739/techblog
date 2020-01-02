<template>
  <el-container>
    <el-main>
      <div v-loading="loading"
           element-loading-text="拼命加载中"
           element-loading-spinner="el-icon-loading"
           element-loading-background="white"
           v-if="articles.length">
        <el-row>
          <span class="desc_left">点赞列表</span>
        </el-row>
        <el-row>
          <el-col :span="5">
            <el-input v-model="search"
                      placeholder="标题或作者搜索"
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
        <el-table :data="articles">
          <el-table-column prop="title"
                           label="标题">
            <template slot-scope="scope">
              <el-button type="text"
                         size="mini"
                         @click="viewByTitle(scope.row)">{{scope.row.title}}</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="avatar"
                           label="头像">
            <template slot-scope="scope">
              <el-avatar :src="scope.row.author.avatar"></el-avatar>
            </template>
          </el-table-column>
          <el-table-column prop="username"
                           label="作者">
            <template slot-scope="scope">
              <el-link @click="viewAuthor(scope.row)">{{scope.row.author.username}}</el-link>
            </template>
          </el-table-column>
          <el-table-column prop="likedNums"
                           label="点赞数"></el-table-column>
          <el-table-column prop="thumbsTime"
                           label="时间"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button plain
                         @click="cancelThumbs(scope.row)">取消点赞</el-button>
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
      </div>
      <div v-else>暂无数据</div>
    </el-main>
  </el-container>
</template>
<script>
import { getThumbsList } from '@/api/article'
import { thumbs } from '@/api/user'
import { Message } from 'element-ui'
export default {
  name: 'ThumbsList',
  inject: ['reload'],
  data () {
    return {
      userID: this.$store.getters.userID,
      articles: [],
      total: 0,
      pageSize: 5,
      pageSizes: [5, 10, 20],
      loading: true,
      currentPage: 1,
      search: ''
    }
  },
  computed: {
    offset () {
      return (this.currentPage - 1) * this.pageSize
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      this.loading = true
      let data = {
        userID: this.userID,
        offset: this.offset,
        limit: this.pageSize,
        search: this.search
      }
      getThumbsList(data).then(res => {
        let data = res.data
        this.articles = data.articles
        this.total = data.total
        this.loading = false
      }).catch(() => { this.loading = false })
    },
    handlerSizeChange (val) {
      this.pageSize = val
      this.handlerCurrentChange(1)
    },
    handlerCurrentChange (val) {
      this.currentPage = val
      this.fetchData()
    },
    handlerSearch () {
      this.handlerCurrentChange(1)
    },
    handlerSearchClear () {
      this.search = ''
      this.handlerCurrentChange(1)
    },
    viewByTitle (row) {
      this.$router.push({ path: '/article/show/' + row.id })
    },
    cancelThumbs (row) {
      let data = {
        articleID: row.id,
        addOne: false
      }
      thumbs(data).then(res => {
        Message.success(res.message)
        this.reload()
      })
    },
    viewAuthor (row) {
      console.log(row.author.id)
      this.$router.push({ path: '/user/profile/' + row.author.id })
    }
  }
}
</script>
