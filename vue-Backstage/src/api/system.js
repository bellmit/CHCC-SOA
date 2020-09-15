import request from '@/utils/request'


//获取图片列表
export function sysUploadList(params) {
  return request({
    url: '/upload/list',
    method: 'post',
    params: params
  })
}
