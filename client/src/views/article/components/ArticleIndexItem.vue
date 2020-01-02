<template>
  <div class="item">
    <div class="title_link">
      <h3>
        <el-link type="primary"
                 @click="view(article.id)"
                 style="color:black">
          {{article.title}}
        </el-link>
      </h3>
    </div>
    <el-row :gutter="10">
      <el-col :span="20">
        <p>
          {{article.summary}}
          <el-link type="success"
                   @click="view(article.id)"
                   icon="el-icon-view">阅读全文</el-link>
        </p>
      </el-col>
      <el-col :span="4"
              v-if="article.summaryImage">
        <el-image :src="article.summaryImage"
                  fit="cover"
                  style="width:150px;height:100px"></el-image>
      </el-col>
    </el-row>

    <div class="desc_right">
      <el-button type="text"
                 @click="thumbs"
                 style="color:black">
        <font-awesome-icon v-if="!liked"
                           :icon="['far', 'thumbs-up']" />
        <font-awesome-icon v-if="liked"
                           style="color:gold"
                           :icon="['fas', 'thumbs-up']" />{{article.likedNums}}
      </el-button>
      <el-button type="text"
                 @click="collect"
                 style="color:black">
        <font-awesome-icon :icon="['far', 'star']"
                           v-if="!collected" />
        <font-awesome-icon :icon="['fas', 'star']"
                           v-if="collected"
                           style="color:gold" />{{article.collectedNums}}
      </el-button>
      <el-button type="text"
                 style="color:black"
                 @click="comment">
        <font-awesome-icon :icon="['far', 'comment']" />{{article.commentNums}}
      </el-button>
      <span class="time">

        <el-button size="mini"
                   type="text"
                   @click="viewAuthor">{{article.author.username}}</el-button>
        {{article.updatedTime}}
      </span>
    </div>

  </div>
</template>
<script>
import { thumbs } from '@/api/user'
import { collect } from '@/api/collection'
import { Message } from 'element-ui'
import VClamp from 'vue-clamp'
export default {
  name: 'ArticleIndexItem',
  components: {
    VClamp
  },
  props: {
    article: {
      type: Object,
      default: null
    },
    authorID: {
      type: String,
      default: ''
    },
    likes: {
      type: Array,
      default: null
    },
    collections: {
      type: Array,
      default: null
    },
    isAuthor: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      liked: this.likes.includes(this.article.id),
      collected: this.collections.includes(this.article.id),
      userID: this.$store.getters.userID
    }
  },
  methods: {
    view (id) {
      this.$router.push({ path: '/article/show/' + id })
    },
    thumbs () {
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
    collect () {
      let data = {
        articleID: this.article.id,
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
    viewAuthor () {
      this.$router.push({ path: '/user/profile/' + this.article.author.id })
    },
    comment () {
      this.$router.push({ path: '/article/show/' + this.article.id })
    }
  }
}
</script>
<style scoped>
.item {
  max-height: 300px;
  border-top: 1px solid gray;
}

.desc_right {
  position: relative;
  margin: 0;
}
.title_link {
  margin-bottom: 10px;
}
.divider {
  margin-top: 10px;
}
.time {
  float: right;
  text-align: center;
  padding: 12px 0px;
}
</style>
