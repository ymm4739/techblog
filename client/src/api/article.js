import request from '@/utils/request'
import qs from 'qs'
export function list () {
  return request({
    url: '/article/list'
  })
}
export function create (data) {
  return request({
    url: '/article/create',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function show (id) {
  return request({
    url: '/article/show/' + id
  })
}

export function update (params) {
  let { id, data } = params
  return request({
    url: '/article/edit/' + id,
    method: 'post',
    data: qs.stringify(data)
  })
}
