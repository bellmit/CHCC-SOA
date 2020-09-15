import request from '@/utils/request'


//banner 列表
export function getList(params) {
  return request({
    url: '/message/list',
    method: 'post',
    params:params
    
  })
}

//提交
export function dataSub(params) {
    return request({
      url: '/message/commit/',
      method: 'post',
      params:params
    })
  }
  
  //详情
export function dataDetail(id) {
    return request({
      url: '/message/detail/'+id,
      method: 'post',
    })
  }

  //删除
  export function dataDel(id) {
    return request({
      url: '/message/delete/'+id,
      method: 'post',
    })
  }
