<template>
  <div>

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

    <div class="desc_right"><span> {{article.updatedTime}}</span>
      <el-link icon="el-icon-chat-dot-square"
               :underline="false">{{article.commentNums}}</el-link>
      <el-link icon="el-icon-collection-tag"
               :underline="false">{{article.collectedNums}}</el-link>
    </div>
    <el-divider></el-divider>

  </div>
</template>
<script>
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
    userID: {
      type: String,
      default: ''
    }
  },
  methods: {
    view (id) {
      this.$router.push({ path: '/user/' + this.userID + '/article/show/' + id })
    }
  }
}
</script>
<style scoped>
.desc_right {
  float: right;
}
.title_link {
  margin-bottom: 10px;
}
</style>
