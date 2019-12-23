<template>
  <div>
    <el-dialog title="文件上传"
               :visible.sync="show"
               center
               width="30%"
               :modal="modal"
               :close-on-click-modal="false"
               @close="closeDialog">
      <input type="file"
             ref="file">
      <div slot="footer">
        <el-button @click="upload">上传</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { uploadImage } from '@/api/upload'
export default {
  name: 'UploadFileDialog',
  data () {
    return {
      file: '',
      show: this.visible,
      modal: false
    }
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    visible () {
      this.show = this.visible
    }
  },
  methods: {
    closeDialog () {
      this.file = ''
      this.$emit('update:visible', false)
    },
    upload () {
      let data = {
        file: this.$refs.file.files[0]
      }

      uploadImage(data).then(res => {
        this.closeDialog()
      }).catch(() => { })
    },
    cancel () {
      this.closeDialog()
    }
  }
}
</script>
