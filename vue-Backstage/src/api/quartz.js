import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/quartz/list',
    method: 'post',
    data: data
  })
}

export function list() {
    return request({
      url: '/quartz/run/list',
      method: 'post'
    })
}

export function deleteJob(data) {
  return request({
    url: '/quartz/deleteJob',
    method: 'post',
    data: data
  })
}

export function pause(data) {
  return request({
    url: '/quartz/pauseJob',
    method: 'post',
    data: data
  })
}

export function resume(data) {
  return request({
    url: '/quartz/resumeJob',
    method: 'post',
    data: data
  })
}

export function run(data) {
  return request({
    url: '/quartz/runJob',
    method: 'post',
    data: data
  })
}

export function add(data) {
  return request({
    url: '/quartz/addJob',
    method: 'post',
    data: data
  })
}

export function edit(data) {
  return request({
    url: '/quartz/editJobTime',
    method: 'post',
    data: data
  })
}