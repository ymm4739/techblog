<template>
  <div class="reply">
    <div class="avatar">
      <el-avatar :src="commenter.avatar"></el-avatar>
    </div>
    <div class="info_box">
      <div>{{commenter.username}}</div>
      <div>回复 {{answer.username}}: {{comment.content}}</div>
      <div class="action_box">{{comment.updatedTime}}
        <div class="action">
          <el-button @click="thumbsComment"
                     type="text"
                     style="color:black;padding:0px">
            <font-awesome-icon :icon="['far', 'thumbs-up']" />
            {{comment.likedNums}}
          </el-button>
          <el-button @click="showReplyForm"
                     type="text"
                     style="color:black;padding:0px">
            <font-awesome-icon :icon="['far', 'comment']" />
          </el-button>
        </div>
      </div>
      <div v-if="isReply">
        <el-form :model="form"
                 ref="replyForm">
          <el-form-item label="回复"
                        prop="reply">
            <el-input v-model="form.reply"></el-input>
          </el-form-item>
          <el-button @click="_reply">回复</el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
import { thumbs } from '@/api/comment'
export default {
  inject: ['reload'],
  components: {
  },
  data () {
    return {
      commentID: this.comment.id,
      isLiked: false,
      articleID: this.comment.articleID,
      commentIndex: this.comment.commentIndex,
      commenter: this.comment.user,
      answer: this.comment.answer,
      isReply: false,
      form: {
        reply: ''
      }
    }
  },
  props: {
    comment: {
      type: Object,
      default: null
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
        this.comment.likedNums += 1
      }).catch(() => { })
    },
    showReplyForm () {
      this.isReply = true
    },
    _reply () {
      let data = {
        articleID: this.comment.articleID,
        commentIndex: this.comment.commentIndex,
        answerID: this.commenter.id,
        userID: this.$store.getters.userID,
        content: this.form.reply
      }
      this.$emit('reply-add', data)
    }
  }
}
</script>
<style scoped>
.reply {
  background-color: #f4f5f0;
  margin-top: 10px;
  padding: 10px;
}
.avatar {
  float: left;
}
.info_box {
  border-bottom: 1px solid #f4f5f5;
  margin-left: 50px;
}
.action {
  display: flex;
  margin-left: auto;
  min-width: 8rem;
}
.action_box {
  margin-top: 1rem;
  display: flex;
  justify-content: space-between;
}
</style>
