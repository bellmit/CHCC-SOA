<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <!-- <breadcrumb class="breadcrumb-container" /> -->

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
            <img :src="img" class="user-avatar"/>
            <i class="iconfont userIcon"/>
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/welcome">
            <el-dropdown-item>
              首页
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item>
            <span @click="drawer = true">个人信息</span>
          </el-dropdown-item>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <el-drawer
      title="编辑个人信息"
      :visible.sync="drawer"
      :before-close="handleClose"
    >
      <div class="drawer__content">
        <el-form :model="user">
          <el-form-item label="昵称:" :label-width="formLabelWidth">
            <el-input v-model="user.nickname"/>
          </el-form-item>
          <el-form-item label="手机号:" :label-width="formLabelWidth">
            <el-input v-model="user.mobile"/>
          </el-form-item>
          <el-form-item label="动态密钥:" :label-width="formLabelWidth">
            <el-input v-model="user.secret"/>
          </el-form-item>
          <el-form-item label="二维码:" :label-width="formLabelWidth">
            <el-image :src="baseUrl + imgPath + user.id"></el-image>
          </el-form-item>
          <el-form-item label="修改密码:" :label-width="formLabelWidth" v-if="!updatePwd">
            <el-link @click="updatePwd = true">点击修改密码</el-link>
          </el-form-item>
          <el-form-item label="新密码:" style="margin-top: -20px" :label-width="formLabelWidth" v-if="updatePwd">
            <el-input v-model="user.password" type="password"/>
          </el-form-item>
          <el-form-item label="确认密码:" style="margin-bottom: 50px" :label-width="formLabelWidth" v-if="updatePwd">
            <el-input v-model="user.newPassword" type="password"/>
          </el-form-item>
          <div class="drawer__content_button">
            <el-button style="width: 200px" @click="cancelForm">取消</el-button>
            <el-button type="primary" style="width: 200px" @click="commitUser" :loading="loading">{{ loading? '提交中...':'确定' }}</el-button>
          </div>
        </el-form>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import headerimg from '@/images/headerimg.gif'
import {_baseUrl, apiLogOut, apiInfo} from '@/api/api'
import { commit } from '@/api/user'

export default {
  data(){
    return {
      baseUrl: _baseUrl,
      imgPath: '/sys/user/generator/',
      updatePwd: false,
      loading: false,
      drawer: false,
      timer: null,
      img: headerimg,
      user: {},
      formLabelWidth: '80px'
    }
  },
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar'
    ])
  },
  created() {
    this.getInfo();
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭?").then(_ => {
        this.drawer = false;
        this.updatePwd = false;
        done();
      })
    },
    cancelForm() {
      this.drawer = false;
      this.loading = false;
      this.updatePwd = false;
      clearTimeout(this.timer)
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    getInfo() {
      apiInfo().then(res => {
        this.user = res.data;
      })
    },
    commitUser() {
      if(this.user.password || this.user.newPassword){
        if(this.user.password != this.user.newPassword){
          this.$notify.error('请重新输入密码!');
          return;
        }
        if(this.user.password.length < 6 && this.user.password.length > 0){
          this.$notify.error('密码不能小于6!');
          return;
        }
      }
      commit(this.user).then(res => {
        if(res.status == 10000){
          this.$notify.success('操作成功');
          this.cancelForm();
          this.getInfo();
        }else{
          this.$notify.error(res.message);
        }
      })
    },
    logout(){
       apiLogOut().then(res => {
          if(res.status === 10000){
             this.$router.push('/')
             window.location.reload()
            }else if(res.status === 20000){

          }
      })
    },
    // async logout() {
    //   await this.$store.dispatch('user/logout')
    //   this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    // }
  }
}
</script>

<style lang="scss" scoped>
.userIcon{
  font-size:26px; color:#fff;
  cursor: pointer;
}
.drawer__content {
  margin: 0 20px 0 20px;
  
  .drawer__content_button {
    position: relative;
    text-align: center;
  }
}
.navbar {
  height: 64px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  background-color: #304156;
  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .user-avatar {
    margin-top: 10px;
    position: relative;
    cursor: pointer;
    width: 40px;
    height: 40px;
    border-radius: 10px;
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
