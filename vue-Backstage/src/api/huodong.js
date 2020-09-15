import request from '@/utils/request'


//banner 列表
export function getList(params) {
  return request({
    url: '/productType/list',
    method: 'post',
    params:params
  })
}

//提交
export function dataSub(params) {
    return request({
      url: '/productType/commit/',
      method: 'post',
      params:params
    })
  }
  
  //详情
export function dataDetail(id) {
    return request({
      url: '/productType/detail/'+id,
      method: 'post',
    })
  }

  //删除
  export function dataDel(id) {
    return request({
      url: '/productType/delete/'+id,
      method: 'post',
    })
  }
