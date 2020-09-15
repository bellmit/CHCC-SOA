import request from '@/utils/request'


//富文本
export function getData() {
  return request({
    url: '/info',
    method: 'post',
  })
}

//富文本提交
export function subData(data) {
  return request({
    url: '/info/update',
    method: 'post',
    data:data
  })
}
