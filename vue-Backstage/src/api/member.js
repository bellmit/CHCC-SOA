import request from '@/utils/request'


//banner 列表
export function getList(params) {
  return request({
    url: '/member/list',
    method: 'post',
    params:params
  })
}

//提交
export function dataSub(params) {
    return request({
      url: '/member/commit/',
      method: 'post',
      params:params
    })
  }
  
  //详情
export function dataDetail(id) {
    return request({
      url: '/member/detail/'+id,
      method: 'post',
    })
  }

  //成员删除
  export function dataDel(id) {
    return request({
      url: '/member/delete/'+id,
      method: 'post',
    })
  }
  //核销删除
  export function dataDel2(id) {
    return request({
      url: '/member/drop/'+id,
      method: 'post',
    })
  }
