<template>
  <div class="comment_box">
    <span class="avatar">
      <el-avatar :src="commenter.avatar"></el-avatar>
    </span>
    <div class="info_box">
      <div>{{commenter.username}}</div>
      <div>{{comment.content}}</div>
      <div class="action_box">{{comment.commentIndex}}楼 {{comment.updatedTime}}
        <div class="action">
          <el-button @click="thumbsComment"
                     type="text"
                     style="color:black;padding:0px">
            <font-awesome-icon :icon="['far', 'thumbs-up']" />
            {{comment.likedNums}}
          </el-button>
          <el-button @click="showReplyForm"
                     type="text"
                     style="color:black; padding:0px">
            <font-awesome-icon :icon="['far', 'comment']" /> {{comment.replyNums}}
          </el-button>
        </div>
      </div>
      <div v-if="isReply">
        <el-form :model="form"
                 ref="reply">
          <el-form-item :label="replyLabel">
            <el-input v-model="form.reply"></el-input>
          </el-form-item>
          <el-button @click="reply">回复</el-button>
        </el-form>
      </div>
      <div>
        <reply-item v-for="data in replyList"
                    :key="data.id"
                    :comment="data"
                    @reply-add="addOneReply" />

        <div class="moreReply">
          <el-button v-if="more"
                     type="text"
                     size="mini"
                     @click="getMoreReply">更多回复</el-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { moreReply, thumbs, comment } from '@/api/comment'
import ReplyItem from './ReplyItem.vue'
export default {
  inject: ['reload'],
  components: {
    ReplyItem
  },
  data () {
    return {
      commentID: this.comment.id,
      isLiked: false,
      articleID: this.comment.articleID,
      commentIndex: this.comment.commentIndex,
      commenter: this.comment.user,
      isReply: false,
      offset: this.replies.length,
      limit: 10,
      replyList: this.replies,
      form: {
        reply: ''
      }
    }
  },
  props: {
    comment: {
      type: Object,
      default: null
    },
    replies: {
      type: Array,
      default: null
    }
  },
  computed: {
    replyLabel () {
      return '回复 ' + this.commenter.username + ':'
    },
    more () {
      return this.comment.replyNums > this.replyList.length
    }
  },
  methods: {
    thumbsComment () {
      let data = {
        commentID: this.commentID,
        isLiked: !this.isLiked
      }
      thumbs(data).then(res => {
        this.isLiked = !this.isLiked
        let num = this.isLiked ? 1 : -1
        comment.likedNums += num
      }).catch(() => { })
    },
    showReplyForm () {
      this.isReply = true
    },
    reply () {
      let data = {
        articleID: this.comment.articleID,
        commentIndex: this.comment.commentIndex,
        answerID: this.commenter.id,
        userID: this.$store.getters.userID,
        content: this.form.reply
      }
      this._reply(data)
    },
    _reply (data) {
      this.isReply = false
      comment(data).then(res => {
        this.limit = this.offset + 1
        this.offset = 0
        this.replyList = []
        this.getMoreReply()
      })
    },
    getMoreReply () {
      let data = {
        articleID: this.articleID,
        commentIndex: this.commentIndex,
        offset: this.offset,
        limit: this.limit
      }
      moreReply(data).then(res => {
        let moreReplies = res.data
        if (moreReplies && moreReplies.length > 0) {
          moreReplies.forEach(element => {
            this.replyList.push(element)
          })
          this.offset += moreReplies.length
        }
      })
    },
    addOneReply (data) {
      this._reply(data)
    }
  }
}
</script>
<style scoped>
.comment_box {
  border-bottom: 1px solid #f4f5f0;
  margin-top: 10px;
}
.info_box {
  margin-left: 50px;
  margin-bottom: 10px;
}
.avatar {
  float: left;
}

.action {
  display: flex;
  margin-left: auto;
  min-width: 8rem;
}
.action_box {
  display: flex;
  justify-content: space-between;
  padding: 10px;
}
.more_reply {
  margin-left: 1rem;
}
</style>
