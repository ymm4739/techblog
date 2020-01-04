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
        <div class="comment_input">
          <el-form :model="form"
                   ref="comment_form">
            <el-form-item label="发表评论">
              <el-input v-model="form.comment"></el-input>
            </el-form-item>
            <el-button type="primary"
                       size="mini"
                       @click="_comment(0, 0)">评论</el-button>
          </el-form>
        </div>
        <div class="comment_detail">
          <comment-item v-for="item in comments"
                        :key="item.id"
                        :comment="item.comment"
                        :replies="item.replies" />
          <el-link v-if="more"
                   @click="moreComment">查看更多评论</el-link>
        </div>
      </div>
    </div>
  </el-main>
</template>
<script>
import { show, get } from '@/api/article'
import { thumbs } from '@/api/user'
import { collect } from '@/api/collection'
import { comment, index } from '@/api/comment'
import { Message } from 'element-ui'
import CommentItem from '@/views/article/components/commentItem.vue'
export default {
  name: 'ArticleShowView',
  inject: ['reload'],
  components: {
    CommentItem
  },
  data () {
    return {
      article: '',
      author: '',
      liked: false,
      collected: false,
      isShow: this.$route.path.split('/')[2] === 'show',
      articleID: this.$route.params.articleID,
      userID: this.$store.getters.userID,
      comments: [],
      commentTotal: 0,
      offset: 0,
      limit: 5,
      form: {
        'comment': ''
      }
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
          this.comments = res.data.comments
          this.commentTotal = res.data.commentTotal
          if (this.comments.length > 0) {
            this.offset += this.comments.length
          }
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
    more () {
      return this.commentTotal > this.offset
    }
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
    },
    _comment (commentIndex, answerID) {
      let data = {
        content: this.form.comment,
        userID: this.userID,
        articleID: this.articleID,
        commentIndex: commentIndex,
        answerID: answerID
      }
      comment(data).then(res => {
        this.limit = this.offset + 1
        this.offset = 0
        this.comments = []
        this.moreComment()
        this.form.comment = ''
        this.article.commentNums += 1
      })
    },
    moreComment () {
      let data = {
        articleID: this.articleID,
        offset: this.offset,
        limit: this.limit
      }
      index(data).then(res => {
        let data = res.data
        if (data && data.length > 0) {
          this.offset += data.length
          data.forEach(item => {
            this.comments.push(item)
          })
        }
      })
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
