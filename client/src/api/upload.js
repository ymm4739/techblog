import request from '@/utils/request'
// import qs from 'qs'
export function uploadImage (data) {
  let { file } = data
  let formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/upload/image',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }
  )
}
