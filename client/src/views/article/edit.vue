<template>
  <div>
    <el-form :model="form"
             :rules="rules"
             show-status>
      <el-form-item label="标题"
                    prop="title">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="摘要"
                    prop="summary">
        <el-upload ref="upload"
                   :http-request="customUpload"
                   :multiple="false"
                   :on-success="uploadSuccess"
                   :file-list="filelist"
                   :show-file-list="false"
                   action="http://localhost:8080/api/upload/image">
          <el-button slot="trigger"
                     size="mini">上传图片</el-button>
          <span>只能上传单张大小小于10M的图片</span>
          <el-popover v-if="form.summaryImage"
                      placement="right"
                      trigger="click"
                      width="450px">
            <el-image :src="form.summaryImage"
                      style="width:400px;height:400px"
                      fit="contain">
            </el-image>
            <el-button slot="reference"
                       type="success"
                       size="mini">查看图片</el-button>
          </el-popover>
        </el-upload>

        <el-input type="textarea"
                  v-model="form.summary"></el-input>
      </el-form-item>
      <mavon-editor v-model="form.content"
                    ref="md"
                    @change="change"
                    @imgAdd="$imgAdd"></mavon-editor>

      <el-form-item>
        <el-button type="primary"
                   @click="submit">发布</el-button>
        <el-button type="primary"
                   @click="save"
                   v-if="!form.isPublished">保存草稿</el-button>
        <el-button @click="cancle">取消</el-button>
      </el-form-item>
    </el-form>
    <upload-file-dialog :visible.sync="uploadVisible" />
  </div>
</template>
<script>
import { create, update, edit } from '@/api/article'
import { uploadImage } from '@/api/upload'
import { Message } from 'element-ui'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import UploadFileDialog from '@/components/UploadFileDialog'
export default {
  name: 'ArticleEditView',
  components: {
    mavonEditor,
    UploadFileDialog
  },
  data () {
    return {
      form: {
        title: '',
        content: '',
        summary: '',
        summaryImage: '',
        isPublished: 0
      },
      html: '',
      rules: {
        title: [{
          required: true,
          message: '输入文章标题',
          trigger: 'blur'
        }],
        summary: [{
          required: true,
          message: '输入文章摘要',
          trigger: 'blur'
        }]
      },
      urlPrefix: '/user/' + this.$route.params.userID + '/article/',
      userID: this.$route.params.userID,
      uploadVisible: false,
      filelist: []
    }
  },
  computed: {
    isUpdated () {
      return !!this.$route.params.articleID
    }
  },
  created () {
    if (this.isUpdated) {
      let id = this.$route.params.articleID
      let userID = this.$route.params.userID
      edit(userID, id).then(res => {
        this.form = res.data
      })
    }
  },
  methods: {
    cancle () {
      this.$router.push({ path: this.urlPrefix + 'list' })
    },
    save (isPublished = 0) {
      let data = {
        title: this.form.title,
        content: this.form.content,
        summary: this.form.summary,
        summaryImage: this.form.summaryImage,
        html: this.html,
        isPublished
      }
      if (this.isUpdated) {
        let id = this.$route.params.articleID
        update({ id, data }, this.userID).then(res => {
          Message.success(res.message)
          this.$router.push({ path: this.urlPrefix + 'list' })
        }).catch(() => { })
      } else {
        create(data, this.userID).then(res => {
          Message.success(res.message)
          this.$router.push({ path: this.urlPrefix + 'list' })
        }).catch(() => { })
      }
    },
    submit () {
      this.save(1)
    },
    change (value, render) {
      this.html = render
    },
    customUpload (file) {
      uploadImage(file).then(res => {
        this.form.summaryImage = res.data
      })
    },
    uploadSuccess (response, file, filelist) {
      console.log(response)
      console.log(file)
      console.log(filelist)
    },
    $imgAdd (pos, $file) {
      let data = {
        file: $file
      }
      uploadImage(data).then(res => {
        this.$refs.md.$img2Url(pos, res.data)
      })
    }
  }
}
</script>
