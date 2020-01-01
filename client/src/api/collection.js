import request from '@/utils/request'
import qs from 'qs'
export function list (data) {
  return request({
    url: '/collection/list?' + qs.stringify(data)
  })
}

export function collect (data) {
  return request({
    url: '/collection',
    method: 'post',
    data: qs.stringify(data)
  })
}
