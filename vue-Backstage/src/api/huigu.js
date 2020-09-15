import request from '@/utils/request'


//banner 列表
export function getList(params) {
  return request({
    url: '/review/list',
    method: 'post',
    params:params
  })
}

//提交
export function dataSub(params) {
    return request({
      url: '/review/commit/',
      method: 'post',
      params:params
    })
  }
  
  //详情
export function dataDetail(id) {
    return request({
      url: '/review/detail/'+id,
      method: 'post',
    })
  }

  //删除
  export function dataDel(id) {
    return request({
      url: '/review/delete/'+id,
      method: 'post',
    })
  }
