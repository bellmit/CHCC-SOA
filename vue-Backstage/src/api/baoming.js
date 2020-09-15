import request from '@/utils/request'


//列表
export function getList(params) {
  return request({
    url: '/enrolls/list',
    method: 'post',
    params:params
  })
}

//提交
export function dataSub(params) {
    return request({
      url: '/enrolls/commit/',
      method: 'post',
      params:params
    })
  }
  
  //详情
export function dataDetail(id) {
    return request({
      url: '/enrolls/detail/'+id,
      method: 'post',
    })
  }

  //删除
  export function dataDel(id) {
    return request({
      url: '/enrolls/delete/'+id,
      method: 'post',
    })
  }
  //活动列表
  export function getType() {
    return request({
      url: '/product/type',
      method: 'post',
    })
  }
