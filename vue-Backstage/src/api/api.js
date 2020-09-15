import request from '@/utils/request'


// export const upload_path = "http://192.168.31.175:8099/api/upload"
// export const member_path = "http://192.168.31.175:8099/api/member/upload"
// export const _baseUrl = "http://192.168.31.175:8099/api/"
 
export const upload_path = "https://dyrc.hemiaoit.com/api/upload"
export const member_path = "https://dyrc.hemiaoit.com/api/member/upload"
export const _baseUrl = "https://dyrc.hemiaoit.com/api/"

//获取二维码
//获取二维码
export function apiGetCode() {
  return request({
    url: '/kaptcha',
    method: 'post'
  })
}
//获取登录
export function apiLogin(params) {
  return request({
    url: '/login',
    method: 'post',
    data: params
  })
}
//退出登录
export function apiLogOut() {
  return request({
    url: '/logout',
    method: 'post',
  })
}
//获取权限列表
export function apiGetMenu() {
  return request({
    url: '/getMenu',
    method: 'post'
  })
}
