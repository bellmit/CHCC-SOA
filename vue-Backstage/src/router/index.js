import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/welcome',
    component: Layout,
    redirect: '/welcome/index',
    children: [
      {
        path: 'index',
        name: 'welcome',
        component: () => import('@/views/console/index'),
        meta: { title: 'Dashboard', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/j',
    component: Layout,
    name: '服务监控',
    // hidden: true,
    meta: {
      title: '服务监控',
      icon: 'wangzhan'
    },
    children: [{
      path: 'nacos',
      // hidden: true,
      component: () =>
        import('@/views/monitor/nacos/index'),
      name: 'Nacos监控',
      meta: {
        title: 'Nacos监控'
      }
    },
    {
      path: 'swagger',
      // hidden: true,
      component: () =>
        import('@/views/monitor/swagger/index'),
      name: 'API文档',
      meta: {
        title: 'API文档'
      }
    },
    {
      path: 'quartzManager',
      // hidden: true,
      component: () =>
        import('@/views/monitor/swagger/index'),
      name: 'Quartz管理',
      meta: {
        title: 'Quartz管理'
      }
    },
    {
      path: 'quartzLog',
      // hidden: true,
      component: () =>
        import('@/views/monitor/swagger/index'),
      name: 'Quartz日志',
      meta: {
        title: 'Quartz日志'
      }
    }
    ]
  },
  {
    path: '/sys',
    component: Layout,
    name: '系统管理',
    hidden: true,
    meta: {
      title: '系统管理',
      icon: 'SystemSetting'
    },
    children: [{
      path: 'menuList',
      hidden: true,
      component: () =>
        import('@/views/menu/menuList'),
      name: '菜单管理',
      meta: {
        title: '菜单管理'
      }
    },
    {
      path: 'roleList',
      hidden: true,
      component: () =>
        import('@/views/role/index'),
      name: '权限管理',
      meta: {
        title: '权限管理'
      }
    },
    {
      path: 'departmentList',
      hidden: true,
      component: () =>
        import('@/views/department/index'),
      name: '部门管理',
      meta: {
        title: '部门管理'
      }
    },
    {
      path: 'userList',
      hidden: true,
      component: () =>
        import('@/views/user/index'),
      name: '用户管理',
      meta: {
        title: '用户管理'
      }
    }
    ]
  },
  {
    path: '/upload',
    hidden: true,
    component: Layout,
    name: '图片管理',
    redirect: '/banner/index',
    children: [
      {
        path: 'index',
        name: '图片管理',
        component: () => import('@/views/upload/index'),
        meta: { title: '图片管理', icon: 'image' },
      }
    ]
  },
  {
    path: '/banner',
    hidden: true,
    component: Layout,
    name: '轮播图管理',
    redirect: '/banner/index',
    children: [
      {
        path: 'index',
        name: '轮播图管理',
        component: () => import('@/views/banner/index'),
        meta: { title: '轮播图管理', icon: 'image' },
      }
    ]
  },
  {
    path: '/member',
    hidden: true,
    component: Layout,
    name: '业主身份管理',
    redirect: '/member/index',
    children: [
      {
        path: 'index',
        hidden: true,
        name: '业主身份管理',
        component: () => import('@/views/member/index'),
        meta: { title: '业主身份管理', icon: 'user' },
      }
    ]
  },
  {
    path: '/hexiao',
    hidden: true,
    component: Layout,
    name: '核销员管理',
    redirect: '/hexiao/index',
    children: [
      {
        path: 'index',
        hidden: true,
        name: '核销员管理',
        component: () => import('@/views/hexiao/index'),
        meta: { title: '核销员管理', icon: 'guanli' },
      }
    ]
  },
  {
    path: '/huodong',
    hidden: true,
    component: Layout,
    name: '活动管理',
    meta: {
      title: '活动管理',
      icon: 'huodong'
    },
    children: [{
      path: 'huodong',
      hidden: true,
      component: () =>
        import('@/views/huodong/guanli'),
      name: '分类管理',
      meta: {
        title: '分类管理',
        icon: 'huodong'
      }
    },
    {
      path: 'list',
      hidden: true,
      component: () =>
        import('@/views/huodong/list'),
      name: '活动列表',
      meta: {
        title: '活动列表',
        icon: 'huodong'
      }
    },
    ]
  },
  {
    path: '/baoming',
    hidden: true,
    component: Layout,
    name: '活动报名',
    redirect: '/baoming/index',
    children: [
      {
        path: 'index',
        hidden: true,
        name: '活动报名',
        component: () => import('@/views/huodong/baoming'),
        meta: { title: '活动报名', icon: 'baoming' },
      }
    ]
  },
  {
    path: '/huigu',
    hidden: true,
    component: Layout,
    name: '精彩回顾',
    redirect: '/huigu/index',
    children: [
      {
        path: 'index',
        hidden: true,
        name: '精彩回顾',
        component: () => import('@/views/huigu/index'),
        meta: { title: '精彩回顾', icon: 'huigu' },
      }
    ]
  },
  {
    path: '/quanyi',
    hidden: true,
    component: Layout,
    name: '业主权益',
    redirect: '/quanyi/index',
    children: [
      {
        path: 'index',
        hidden: true,
        name: '业主权益',
        component: () => import('@/views/quanyi/index'),
        meta: { title: '业主权益', icon: 'quanyi' },
      }
    ]
  },
  {
    path: '/xieyi',
    hidden: true,
    component: Layout,
    name: '用户协议',
    redirect: '/xieyi/index',
    children: [
      {
        path: 'index',
        hidden: true,
        name: '用户协议',
        component: () => import('@/views/xieyi/index'),
        meta: { title: '用户协议', icon: 'xieyi' },
      }
    ]
  },
  // {
  //   path: '/shop',
  //   component: Layout,
  //   name: '商户管理',
  //   redirect: '/shop/index',
  //   children: [
  //     {
  //       path: 'index',
  //       name: '商户管理',
  //       component: () => import('@/views/shop/index'),
  //       meta: { title: '商户管理', icon: 'message' },
  //     }
  //   ]
  // },
  {
    path: '/shop',
    hidden: true,
    component: Layout,
    name: '商户管理',
    meta: {
      title: '商户管理',
      icon: 'huodong'
    },
    children: [{
      path: 'index',
      hidden: true,
      component: () =>
        import('@/views/shop/index'),
      name: '商户管理列表',
      meta: {
        title: '商户管理列表',
        icon: 'message'
      }
    },
    {
      path: 'hexiaoyuan',
      hidden: true,
      component: () =>
        import('@/views/shop/shophexiaoyuan'),
      name: '商户核销员管理',
      meta: {
        title: '商户核销员管理',
        icon: 'huodong'
      }
    },
     {
      path: 'hexiaoorder',
      hidden: true,
      component: () =>
        import('@/views/shop/shophexiaolist'),
      name: '商户核销记录',
      meta: {
        title: '商户核销记录',
        icon: 'huodong'
      }
    }
    ]
  },
  {
    path: '/message',
    hidden: true,
    component: Layout,
    name: '留言管理',
    redirect: '/message/index',
    children: [
      {
        path: 'index',
        hidden: true,
        name: '留言管理',
        component: () => import('@/views/message/index'),
        meta: { title: '留言管理', icon: 'message' },
      }
    ]
  },
  
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
