<template>
  <el-main class="">
    <div class="main">
      <h2 v-if="!article">暂无已发布的文章</h2>
      <div v-if="article">
        <el-avatar :src="author.avatar"
                   style="float:left"></el-avatar>
        <div class="author_info_box">
          <span>
            {{author.username}}
          </span>
          <div>
            {{article.updatedTime}}
            <span class="data"
                  style="float: right">
              <font-awesome-icon :icon="['far', 'thumbs-up']" /> {{article.likedNums}}
              <font-awesome-icon :icon="['far', 'comment']" /> {{article.commentNums}}
              <font-awesome-icon :icon="['far', 'star']" /> {{article.collectedNums}}
            </span>
          </div>
        </div>
        <div>
          <h3>{{article.title}}</h3>
          <div v-html="article.html"></div>
        </div>
        <div class="info"
             v-if="isShow">
          <el-button type="text"
                     style="color:black"
                     @click="_thumbs">
            <font-awesome-icon v-if="!liked"
                               size="2x"
                               :icon="['far', 'thumbs-up']" />
            <font-awesome-icon v-if="liked"
                               style="color:gold"
                               :icon="['fas', 'thumbs-up']"
                               size="2x" />
          </el-button>

          <el-button type="text"
                     style="color:black"
                     @click="_collect">
            <font-awesome-icon :icon="['far', 'star']"
                               size="2x"
                               v-if="!collected" />
            <font-awesome-icon :icon="['fas', 'star']"
                               size="2x"
                               style="color:gold"
                               v-if="collected" />
          </el-button>
          <el-button type="text"
                     style="color:black">
            <font-awesome-icon :icon="['far', 'comment']"
                               size="2x" />
          </el-button>
        </div>
      </div>
    </div>
  </el-main>
</template>
<script>
import { show, get } from '@/api/article'
import { thumbs } from '@/api/user'
import { collect } from '@/api/collection'
import { Message } from 'element-ui'
export default {
  name: 'ArticleShowView',
  data () {
    return {
      article: '',
      author: '',
      liked: false,
      collected: false,
      isShow: this.$route.path.split('/')[2] === 'show',
      articleID: this.$route.params.articleID,
      userID: this.$store.getters.userID
    }
  },
  created () {
    let articleID = this.$route.params.articleID
    let readerID = this.$store.getters.userID
    if (this.isShow) {
      show(articleID, readerID).then(res => {
        if (res.data) {
          this.article = res.data.article
          this.author = this.article.author
          this.liked = res.data.likes.includes(parseInt(articleID))
          this.collected = res.data.collections.includes(parseInt(articleID))
        }
      }).catch(() => { })
    } else {
      get(articleID).then(res => {
        if (res.data) {
          this.article = res.data
          this.author = this.article.author
        }
      })
    }
  },
  computed: {

  },
  methods: {
    _thumbs () {
      let data = {
        articleID: this.article.id,
        liked: !this.liked
      }
      thumbs(data).then(res => {
        this.liked = !this.liked
        let num = this.liked ? 1 : -1
        this.article.likedNums += num
        Message.success(res.message)
      }).catch(() => { })
    },
    _collect () {
      let data = {
        articleID: this.articleID,
        userID: this.userID,
        addOne: !this.collected
      }
      collect(data).then(res => {
        this.collected = !this.collected
        let num = this.collected ? 1 : -1
        this.article.collectedNums += num
        Message.success(res.message)
      }).catch(() => { })
    }
  }
}
</script>
<style scoped>
.info {
  position: relative;
}
.main {
  background-color: white;
  padding: 20px;
}
.author_info_box {
  padding-left: 50px;
}
</style>
