import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function getList(params) {
  return request({
    url: '/sys/user/list',
    method: 'post',
    params: params
  })
}

export function findInfo(id) {
  return request({
    url: '/sys/user/detail/' + id,
    method: 'post'
  })
}

export function findRoles(id) {
  return request({
    url: '/sys/user/findRoles/' + id,
    method: 'post'
  })
}

export function enable(params) {
  return request({
    url: '/sys/user/enable',
    method: 'post',
    params: {
      id: params
    }
  })
}

export function disable(params) {
  return request({
    url: '/sys/user/disable',
    method: 'post',
    params: {
      id: params
    }
  })
}

export function reset(params) {
  return request({
    url: '/sys/user/reset',
    method: 'post',
    params: {
      id: params
    }
  })
}

export function commit(params) {
  return request({
    url: '/sys/user/commitUser',
    method: 'post',
    params: params
  })
}

export function commitRole(params) {
  return request({
    url: '/sys/user/commitRole',
    method: 'post',
    params: params
  })
}
