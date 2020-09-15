import request from '@/utils/request'


//banner 列表
export function getList(params) {
  return request({
    url: '/shop/list',
    method: 'post',
    params:params
  })
}

//提交
export function dataSub(params) {
    return request({
      url: '/shop/commit/',
      method: 'post',
      data:params
    })
  }
  
  //详情
export function dataDetail(id) {
    return request({
      url: '/shop/detail/'+id,
      method: 'post',
    })
  }

  //成员删除
  export function dataDel(id) {
    return request({
      url: '/shop/delete/'+id,
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
//获取商户名称
export function getType() {
  return request({
    url: '/shop/shopname/',
    method: 'post',
  })
}

//提交核销员信息
export function dataHxSub(params) {
  return request({
    url: '/shophexiaoyuan/commit/',
    method: 'post',
    data:params
  })
}
    //商户核销员删除
export function dataHxDel(id) {
      return request({
        url: '/shophexiaoyuan/delete/'+id,
        method: 'post',
      })
}
 

//banner 列表
export function getHxList(params) {
  return request({
    url: '/shophexiaoyuan/list',
    method: 'post',
    params:params
  })
}