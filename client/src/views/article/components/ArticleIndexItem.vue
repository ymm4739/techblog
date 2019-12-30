<template>
  <div>
    <div v-if="!isAuthor">
      <el-avatar :src="article.author.avatar"></el-avatar>
      <span>
        <el-link @click="viewAuthor">{{article.author.username}}</el-link>
      </span>
    </div>
    <div class="title_link">
      <el-link type="primary"
               @click="view(article.id)">{{article.title}}</el-link>
    </div>

    <el-row :gutter="10">
      <el-col :span="4"
              v-if="article.summaryImage">
        <el-image :src="article.summaryImage"
                  fit="cover"
                  style="height:150px"></el-image>
      </el-col>
      <el-col :span="20">
        <p>{{article.summary}} <el-link type="success"
                   @click="view(article.id)"
                   icon="el-icon-view">阅读全文</el-link>
        </p>
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
                 style="color:black">
        <font-awesome-icon :icon="['far', 'comment']" />{{article.commentNums}}
      </el-button>
      <span class="time">{{article.updatedTime}}</span>
    </div>
    <el-divider class="divider"></el-divider>

  </div>
</template>
<script>
import { thumbs } from '@/api/user'
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
    isAuthor: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      liked: this.likes.includes(this.article.id),
      collected: false
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
      this.collected = !this.collected
    },
    viewAuthor () {
      this.$router.push({ path: '/user/profile/' + this.article.author.id })
    }
  }
}
</script>
<style scoped>
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
