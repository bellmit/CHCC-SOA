import request from '@/utils/request'


//获取上传类型
export function articleTypes() {
  return request({
    url: '/config/types',
    method: 'post',
  })
}

//获取文章列表
export function articleList(params) {
    return request({
      url: '/config/list',
      method: 'post',
      params:params
    })
  }
  
  //获取文章详情
export function articleDetail(id) {
    return request({
      url: '/config/detail/'+id,
      method: 'post',
    })
  }
  
  //文章修改
  export function articleCommit(params) {
    return request({
      url: '/config/commit',
      method: 'post',
      params:params
    })
  }
