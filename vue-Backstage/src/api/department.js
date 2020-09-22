import request from '@/utils/request'

export function list(data) {
    return request({
      url: '/sys/department/list',
      method: 'post',
      data:data
    })
}

export function findId(id) {
    return request({
      url: '/sys/department/detail/'+id,
      method: 'post'
    })
}

export function findUser(id) {
    return request({
      url: '/sys/department/findUser/'+id,
      method: 'post'
    })
}

export function commitUser(data) {
    return request({
        url: '/sys/department/commitUser',
        method: 'post',
        data: data
    })
}

export function commit(data) {
    return request({
      url: '/sys/department/commit',
      method: 'post',
      data:data
    })
}

export function changeStatus(data) {
    return request({
      url: '/sys/department/changeStatus',
      method: 'post',
      data:data
    })
}

export function deleteDepartment(data) {
    return request({
      url: '/sys/department/delete',
      method: 'post',
      data:data
    })
}