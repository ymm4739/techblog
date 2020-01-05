<template>
  <el-main>
    <div class="profile">
      <div class="avatar_cropper">
        <div class="avatar_box">
          <img v-if="avatar"
               :src="avatar"
               class="avatar">
          <el-upload class="avatar-uploader"
                     action="http://localhost:8080/api/user/change/avatar"
                     :before-upload="beforeUpload"
                     :auto-upload="false"
                     :show-file-list="false"
                     :on-change="uploadImage"
                     ref="upload">
            <i v-if="!avatar"
               class="el-icon-plus avatar-uploader-icon"></i>
            <el-button slot="trigger"
                       size="mini">选择头像</el-button>
            <div slot="tip">图片大小不能超过5M</div>
          </el-upload>
        </div>
        <div v-if="isShowCropper"
             class="cropper_box">
          <div class="cropper">
            <vue-cropper style="width:400px;height:400px"
                         ref="cropper"
                         :img="option.img"
                         :outputSize="option.outputSize"
                         :outputType="option.outputType"
                         :info="option.info"
                         :canMoveBox="false"
                         :fixedBox="true"
                         :centerBox="true"
                         :autoCrop="option.autoCrop"
                         :autoCropWidth="option.autoCropWidth"
                         :autoCropHeight="option.autoCropHeight"
                         :mode="option.mode"
                         @realTime="realTime"></vue-cropper>
          </div>
          <div class="preview_box">
            <h2 align="center">头像预览</h2>
            <div class="show-preview">
              <div :style="previews.div"
                   class="preview">
                <img :src="previews.url"
                     :style="previews.img">
              </div>
            </div>
            <div>
              <el-button @click="upload">上传</el-button>
              <el-button @click="isShowCropper = false">取消</el-button>
            </div>
          </div>
        </div>
      </div>
      <el-form>
        <el-form-item label="用户名">{{user.username}}
          <el-input v-if="modify"
                    :placeholder="username">
          </el-input>
        </el-form-item>
        <el-form-item label="邮箱">{{user.email}}
          <el-input v-if="modify"
                    :placeholder="email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary"
                     @click="update">修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-main>
</template>
<script>
import { Message } from 'element-ui'
import { profile } from '@/api/user'
import { VueCropper } from 'vue-cropper'
export default {
  name: 'Profile',
  data () {
    return {
      user: '',
      modify: false,
      activated: this.$store.state.user.isValidEmail,
      userID: this.$route.params.userID,
      isShowCropper: false,
      option: {
        img: '', // 裁剪图片的地址
        info: true, // 裁剪框的大小信息
        outputSize: 1, // 裁剪生成图片的质量
        outputType: 'jpeg', // 裁剪生成图片的格式
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 200, // 默认生成截图框宽度
        autoCropHeight: 200, // 默认生成截图框高度
        mode: 'cover'
      },
      previews: {}
    }
  },
  components: {
    VueCropper
  },
  created () {
    this.fetchData()
  },
  computed: {
    username () {
      return this.user.username
    },
    email () {
      return this.user.email
    },
    avatar () {
      return this.user.avatar
    }

  },
  methods: {
    update () {
      this.modify = true
    },
    activate () {
      let data = {
        email: this.email
      }
      this.$store.dispatch('user/sendActivatedEmail', data).then(res => {
        Message.success(res.message)
      })
    },
    fetchData () {
      profile(this.userID).then(res => {
        this.user = res.data
      })
    },
    showCropper () {
      this.isCropper = true
    },
    realTime (data) {
      this.previews = data
    },
    beforeUpload (file) {
      if (!file || file.size <= 0) {
        this.isShowCropper = false
        this.$message.error('未选择图片')
        return false
      }
      let limit10m = file.size / 1024 / 1024 > 5
      if (limit10m) {
        this.$message.error('图片大小不能超过5M')
        return false
      }
      return true
    },
    upload () {
      this.$refs.cropper.getCropBlob(res => {
        this.$store.dispatch('user/changeAvatar', { file: res }).then(res => {
          this.user = this.$store.getters.user
          this.$message.success('头像更换成功')
          this.isShowCropper = false
        })
      })
    },
    uploadImage (file, filelist) {
      if (this.isShowCropper === false) {
        let reader = new FileReader()
        reader.readAsDataURL(file.raw)
        reader.onload = () => {
          this.option.img = reader.result
          this.isShowCropper = true
        }
      } else {
        this.option.img = ''
        this.isShowCropper = false
      }
    }
  }
}
</script>
<style scoped>
.profile {
  top: 10%;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border: 2px solid #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 200px;
  height: 200px;
  line-height: 200px;
  text-align: center;
}
.avatar {
  width: 200px;
  height: 200px;
  display: block;
}
.show-preview {
  display: flex;
  justify-content: center;
  margin: 10px;
}

.preview {
  border-radius: 50%;
  overflow: hidden;
  border: 1px solid #cccccc;
  background: #cccccc;
}

.cropper_box {
  display: flex;
  justify-content: flex-start;
}

.cropper {
  display: flex;
}

.preview_box {
  display: block;
  margin-left: 30px;
}
.avatar-cropper {
  display: flex;
  justify-content: flex-start;
}
.avatar-box {
  display: flex;
}
</style>
