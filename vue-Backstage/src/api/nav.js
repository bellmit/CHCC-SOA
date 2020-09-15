import request from '@/utils/request'


//获取文章列表
export function articleList(params) {
    return request({
      url: '/enum/list',
      method: 'post',
      params:params
    })
  }
  
  //获取文章详情
export function articleDetail(id) {
    return request({
      url: '/enum/detail/'+id,
      method: 'post',
    })
  }
  
  //文章修改
  export function articleCommit(params) {
    return request({
      url: '/enum/commit',
      method: 'post',
      params:params
    })
  }
  
//状态启用
export function articleUnable(id) {
    return request({
        url: '/enum/enable/'+id,
        method: 'post'
    })
}
    
//状态禁用
export function articleDisable(id) {
    return request({
        url: '/enum/disable/'+id,
        method: 'post'
    })
}