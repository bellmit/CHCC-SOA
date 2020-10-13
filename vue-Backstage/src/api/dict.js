import request from '@/utils/request'

export function list(data) {
    return request({
      url: '/sys/dict/list',
      method: 'post',
      data:data
    })
}

export function findId(id) {
    return request({
      url: '/sys/dict/detail/'+id,
      method: 'post'
    })
}

export function commit(data) {
    return request({
      url: '/sys/dict/commit',
      method: 'post',
      data:data
    })
}

export function changeStatus(data) {
    return request({
      url: '/sys/dict/changeStatus',
      method: 'post',
      data:data
    })
}

export function deletedict(data) {
    return request({
      url: '/sys/dict/delete',
      method: 'post',
      data:data
    })
}