import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/sys/role/list',
    method: 'post',
    params: params
  })
}

export function findInfo(id) {
  return request({
    url: '/sys/role/detail/' + id,
    method: 'post'
  })
}

export function getRoles() {
  return request({
    url: '/sys/role/getRoles',
    method: 'post'
  })
}

export function findTree(id) {
  return request({
    url: '/sys/role/findTree/' + id,
    method: 'post'
  })
}

export function enable(id) {
  return request({
    url: '/sys/role/enable/' + id,
    method: 'post'
  })
}

export function disable(id) {
  return request({
    url: '/sys/role/disable/' + id,
    method: 'post'
  })
}

export function commit(params) {
  return request({
    url: '/sys/role/commit',
    method: 'post',
    params: params
  })
}

export function commitTree(params) {
  return request({
    url: '/sys/role/commitTree',
    method: 'post',
    params: params
  })
}
