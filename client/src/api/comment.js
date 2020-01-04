import request from '@/utils/request'
import qs from 'qs'
export function list (data) {
  return request({
    url: '/comment/list?' + qs.stringify(data)
  })
}

export function $delete (data) {
  return request({
    url: '/comment/delete',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function comment (data) {
  return request({
    url: '/comment',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function moreReply (data) {
  return request({
    url: '/comment/reply?' + qs.stringify(data),
    method: 'get'
  })
}

export function thumbs (data) {
  return request({
    url: '/comment/thumbs',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function index (data) {
  return request({
    url: '/comment/index?' + qs.stringify(data)
  })
}
