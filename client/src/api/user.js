import request from '@/utils/request'
import api from '@/constant/api'
import qs from 'qs'
export function login (data) {
  return request({
    url: api.login,
    method: 'post',
    data: qs.stringify(data)
  })
}

export function registry (data) {
  return request({
    url: api.registry,
    method: 'post',
    data: qs.stringify(data)
  })
}

export function getInfo () {
  return request({
    url: api.userInfo,
    method: 'get'
  })
}
export function profile (userID) {
  return request({
    url: '/user/profile/' + userID
  })
}
export function logout () {
  return request({
    url: api.logout,
    method: 'post'
  })
}

export function sendActivatedEmail (data) {
  return request({
    url: api.sendActivatedEmail,
    method: 'post',
    data: qs.stringify(data)
  })
}

export function activateEmail (data) {
  return request({
    url: '/user/email/activate?' + qs.stringify(data),
    method: 'get'
  })
}

export function changePassword (data) {
  return request({
    url: '/user/password/change',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function resetPasswordByEmail (data) {
  return request({
    url: '/user/password/reset',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function getVerifyCode (data) {
  return request({
    url: '/user/password/getVerifyCode?',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function changeAvatar (data) {
  let { file } = data
  let formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/user/avatar/change',
    method: 'post',
    data: formData,
    headers: {
      'Content-type': 'multipart/form-data'
    }
  })
}

export function thumbs (data) {
  let { articleID, liked } = data
  let params = {
    articleID,
    addOne: liked
  }
  return request({
    url: '/user/thumbs/article',
    method: 'post',
    data: qs.stringify(params)
  })
}
