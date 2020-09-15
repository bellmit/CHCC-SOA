<template>
  <div :class="{'has-logo':showLogo}">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <div  class="avue-logo">
        <span class="avue-logo_subtitle">RC</span>
        <span class="avue-logo_title">融创后台管理系统</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="false"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item v-for="route in routes" :key="route.path" :item="route" :base-path="route.path" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Logo from './Logo'
import SidebarItem from './SidebarItem'
import variables from '@/styles/variables.scss'
import {apiGetMenu} from '@/api/api'
export default {
  components: { SidebarItem, Logo },
  data() {
    return {
      menus:'',
      routes:[]
    }
  },
  computed: {
    ...mapGetters([
      'sidebar'
    ]),
    // routes() {
    //   return this.$router.options.routes
    //   const router = this.$router.options.routes
    //   console.log(router)
    //   return router
    // },
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo
    },
    variables() {
      return variables
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  },
  created(){
    this.init()   
  },
  methods: {
    init() {
       apiGetMenu().then(res => {
          if(res.status === 10000){
           this.menus = res.data  

            if(this.menus){
              // this.$root.reload()
              const router = this.$router.options.routes
              const routers = []
              router.forEach(m => {
                if (m.path === '/home') {
                  routers.push(m)
                }
                this.menus.forEach(menu => {
                  if (m.name === menu.name) {
                    if (m.children && m.children.length > 0){
                      const array = []
                      this.menus.forEach(u => {
                        m.children.forEach(c => {
                          if (c.name === u.name) {
                            array.push(c)
                          }
                        })
                      })
                      m.children = array

                    }
                    routers.push(m)
                  }
                })
              })
              this.routes = routers
            }



          }else if(res.status === 20000){
            this.$message.error(res.message);
          }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.hideSidebar .avue-logo_subtitle{
  display: block;
}
.hideSidebar .avue-logo_title{
  display: none;
}
.avue-logo_subtitle{
  display: none
}
.avue-logo_title {
    display: block;
    text-align: center;
    font-size: 16px;
    font-weight: 400;
    color: #fff;
}
.avue-logo_subtitle {
    text-align: center;
    font-size: 18px;
    font-weight: 700;
    color: #fff;
}
.avue-logo {

    top: 0;
    left: 0;
    width: 100%;
    height: 64px;
    line-height: 64px;
    background-color: #17B0FC;
    font-size: 20px;
    overflow: hidden;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-box-shadow: 0 1px 2px 0 rgba(0,0,0,.15);
    box-shadow: 0 1px 2px 0 rgba(0,0,0,.15);
    color: hsla(0,0%,100%,.8);
    z-index: 1024;
}
</style>
