import request from '@/utils/request'
import qs from 'qs'
export function index (authorID, readerID) {
  let data = {
    readerID: readerID
  }
  return request({
    url: '/article/index/' + authorID + '?' + qs.stringify(data)

  })
}
export function list (authorID) {
  let query = {
    authorID: authorID
  }
  return request({
    url: '/article/list?' + qs.stringify(query)
  })
}
export function create (data) {
  return request({
    url: '/article/create',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function show (articleID, readerID) {
  return request({
    url: '/article/show/' + articleID + '?' + qs.stringify({ readerID })
  })
}

export function update (params) {
  let { articleID, data } = params

  return request({
    url: '/article/save/' + articleID,
    method: 'post',
    data: qs.stringify(data)
  })
}

export function _delete (articleID) {
  return request({
    url: '/article/delete/' + articleID,
    method: 'post'
  })
}

export function get (articleID) {
  return request({
    url: '/article/' + articleID
  })
}
