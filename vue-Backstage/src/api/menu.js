import request from '@/utils/request'

export function addMenu(params) {
  return request({
    url: '/sys/menu/add',
    method: 'post',
    params: params
  })
}

export function editMenu(params) {
  return request({
    url: '/sys/menu/edit',
    method: 'post',
    params: params
  })
}

export function getList(params) {
  return request({
    url: '/sys/menu/list',
    method: 'post',
    params: params
  })
}

export function getTree() {
  return request({
    url: '/sys/menu/tree',
    method: 'post'
  })
}

export function getMenus() {
  return request({
    url: '/sys/menu/menus',
    method: 'post'
  })
}

export function findInfo(id) {
  return request({
    url: '/sys/menu/detail/' + id,
    method: 'post'
  })
}

export function removeMenu(id) {
  return request({
    url: '/sys/menu/remove/' + id,
    method: 'post'
  })
}
