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
              <el-input type="textarea"
                        v-model="form.comment"></el-input>
            </el-form-item>
            <el-button type="primary"
                       size="mini"
                       @click="_comment(0)">评论</el-button>
          </el-form>
        </div>
        <div class="comment_detail">
          <div v-for="comment in comments"
               :key="comment.id">
            <p>{{comment.commenter.username}} <span v-if="comment.responseID">回复 {{comment.response.username}}</span> ：</p>
            <p>{{comment.content}}</p>
          </div>
        </div>
      </div>
    </div>
  </el-main>
</template>
<script>
import { show, get } from '@/api/article'
import { thumbs } from '@/api/user'
import { collect } from '@/api/collection'
import { comment } from '@/api/comment'
import { Message } from 'element-ui'
export default {
  name: 'ArticleShowView',
  inject: ['reload'],
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
    },
    _comment (responseID) {
      let data = {
        content: this.form.comment,
        userID: this.userID,
        articleID: this.articleID,
        responseID: responseID
      }
      comment(data).then(res => {
        Message.success(res.message)
        this.reload()
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
