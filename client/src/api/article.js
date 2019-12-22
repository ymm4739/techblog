import request from '@/utils/request'
import qs from 'qs'
export function index (userID) {
  let urlPrefix = '/user/' + userID
  return request({
    url: urlPrefix + '/article/index'

  })
}
export function list (userID) {
  let urlPrefix = '/user/' + userID
  return request({
    url: urlPrefix + '/article/list'
  })
}
export function create (data, userID) {
  let urlPrefix = '/user/' + userID

  return request({
    url: urlPrefix + '/article/create',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function show (userID, id) {
  let urlPrefix = '/user/' + userID

  return request({
    url: urlPrefix + '/article/show/' + id
  })
}

export function update (params, userID) {
  let { id, data } = params
  let urlPrefix = '/user/' + userID

  return request({
    url: urlPrefix + '/article/save/' + id,
    method: 'post',
    data: qs.stringify(data)
  })
}

export function _delete (userID, articleID) {
  return request({
    url: '/user/' + userID + '/article/delete/' + articleID,
    method: 'post'
  })
}

export function edit (userID, articleID) {
  return request({
    url: '/user/' + userID + '/article/edit/' + articleID
  })
}
