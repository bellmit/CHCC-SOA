import request from '@/utils/request'


//banner 列表
export function getList(params) {
  return request({
    url: '/product/list',
    method: 'post',
    params:params
  })
}

//提交
export function dataSub(params) {
    return request({
      url: '/product/commit/',
      method: 'post',
      data:params
    })
  }
  
  //详情
export function dataDetail(id) {
    return request({
      url: '/product/detail/'+id,
      method: 'post',
    })
  }

  //删除
  export function dataDel(id) {
    return request({
      url: '/product/delete/'+id,
      method: 'post',
    })
  }
  //分类
  export function getType() {
    return request({
      url: '/product/type',
      method: 'post',
    })
  }
