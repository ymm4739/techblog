import request from '@/utils/request'
import qs from 'qs'
export function list () {
  return request({
    url: '/article/list'
  })
}
export function create (data) {
  return request({
    url: '/article/edit',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function show (data) {
  return request({
    url: '/article/show/' + data
  })
}
