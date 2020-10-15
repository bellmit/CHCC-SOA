import request from '@/utils/request'


// export const upload_path = "http://192.168.31.175:8099/api/upload"
// export const member_path = "http://192.168.31.175:8099/api/member/upload"
export const _baseUrl = "http://123.57.27.53:8099/api/"

//监控
export const nacos_path = "http://123.57.27.53:8099/nacos-api"
export const swagger_path = "http://123.57.27.53:8099/swagger-api"
export const sentinel_path = "http://123.57.27.53:8099/sentinel-api"
 
export const upload_path = "https://dyrc.hemiaoit.com/api/upload"
export const member_path = "https://dyrc.hemiaoit.com/api/member/upload"
// export const _baseUrl = "http://192.161.1.146:8099/api/"

//监控
// export const nacos_path = "http://192.168.1.146:8848/nacos"
// export const swagger_path = "http://123.57.27.53:8089/doc.html"
// export const sentinel_path = "http://123.57.27.53:8888"

//获取二维码
//获取二维码
export function apiGetCode() {
  return request({
    url: '/kaptcha',
    method: 'post'
  })
}

export function apiInfo() {
  return request({
    url: '/info',
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
