<template>
  <div class="login-container">

		<div class="login-weaper">
			<div class="login-left">

			</div>

			<div class="login-border">
				<div class="login-main">
					<form class="el-form login-form">
            <h4 class="login-title">
              后台管理系统
            </h4>
						<div class="el-form-item">
							<div class="el-form-item__content">
								<div class="el-input el-input--small el-input--prefix">
									<input :class="loginRules.username.success?'success_bottom':''" type="text" @blur="isUsername(loginForm.username)" v-model="loginForm.username" autocomplete="off" placeholder="请输入用户名" class="el-input__inner">
                  <span class="el-input__prefix">
                    <i class="el-icon-user"></i>
									</span>
                  <span class="el-input__suffix" v-if="loginRules.username.success">
                    <i class="el-icon-circle-check"></i>
									</span>
                  <div class="el-form-item__error"  v-if="loginRules.username.flag">
                    请输入用户名
                  </div>
								</div>
							</div>
						</div>
						<div class="el-form-item">
							<div class="el-form-item__content">
								<div class="el-input el-input--small el-input--prefix">
									<input :class="loginRules.password.success?'success_bottom':''" type="password" @blur="isPassword(loginForm.password)" v-model="loginForm.password" autocomplete="off" placeholder="请输入密码" class="el-input__inner">
									<span class="el-input__prefix">
									  <i class="el-icon-lock"></i>
									</span>
                  <span class="el-input__suffix" v-if="loginRules.password.success">
                    <i class="el-icon-circle-check"></i>
									</span>
                  <div class="el-form-item__error"  v-if="loginRules.password.flag">
                    密码不能小于6位
                  </div>
								</div>
							</div>
						</div>


						<div class="el-form-item el-form-item--feedback is-required">
							<div class="el-form-item__content">
								<div class="el-row" span="24">
									<div class="el-col el-col-16">
										<div class="el-input el-input--small el-input--prefix">
											<input :class="loginRules.code.success?'success_bottom':''" type="text"
                       @blur="isCode(loginForm.code)"
                        v-model="loginForm.code"
                        placeholder="验证码"
                        maxlength = '5'
                       autocomplete="off"  class="el-input__inner">
                      <span class="el-input__prefix">
                        <i class="el-icon-setting"></i>
											</span>
                      <div class="el-form-item__error" v-if="loginRules.code.flag">
                        请输入正确验证码
                      </div>
										</div>
									</div>
									<div class="el-col el-col-8">
										<div class="login-code">
                       <img :src="imgcode" alt="" @click="flsuhImg()">
                    </div>
									</div>
								</div>
								<!---->
							</div>
						</div>
						<div class="el-form-item">
							<div class="el-form-item__content" style="margin-left: 0px;">
                <button type="button" class="el-button" @click="handleLogin()">
									<span>登录</span>
                </button>
							</div>
						</div>


					</form>

				</div>
			</div>

		</div>

  </div>
</template>

<script>
import {apiLogin,_baseUrl} from '@/api/api'
import {Decrypt,Encrypt} from '@/components/aes/aes'  //aes
export default {
  name: 'Login',
  data() {
    return {
      imgcode:'',
      loginForm: {
        username: '',
        password: '',
        code:''
      },
      loginRules: {
        username:  { flag: false, message: '',success:false},
        password: { flag: false, message: '',success:false},
        code: { flag: false, message: '',success:false},
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
     this.flsuhImg()
  },
  methods: {
    isUsername(value){
      if (!value) {
        this.loginRules.username.flag = true
        this.loginRules.username.success = false
      }else{
        this.loginRules.username.flag = false
        this.loginRules.username.success = true
      }
    },
    isPassword(value){
      if (value.length < 6) {
        this.loginRules.password.flag = true
        this.loginRules.password.success = false
      } else {
        this.loginRules.password.flag = false
        this.loginRules.password.success = true
      }
    },
    isCode(value){
      if (value.length != 5) {
        this.loginRules.code.flag = true
        this.loginRules.code.success = false
      }else{
        this.loginRules.code.flag = false
        this.loginRules.code.success = true
      }
    },
    flsuhImg() {
      // this.imgcode = 'http://192.168.0.104:9528/api/kaptcha?code=' + Math.random()*(9-3+1)+8,10
      this.imgcode = _baseUrl+'kaptcha?code=' + Math.random()*(9-3+1)+8,10
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
        this.isUsername(this.loginForm.username)
        this.isPassword(this.loginForm.password)
        this.isCode(this.loginForm.code)
        if (this.loginRules.username.success&&this.loginRules.password.success&&this.loginRules.code.success) {
          this.password = this.loginForm.password
          let data = this.loginForm
          data.password = Encrypt(this.loginForm.password);
          apiLogin(data).then(res => {
              if(res.status == 10000){
                console.log('登录成功')
                this.$message.success("操作成功");
                 this.$router.push({ path:'/welcome'})
              }else{
                this.loginForm.password = this.password
                console.log(this.loginForm)
                this.flsuhImg()
              }
          })
        } else {
          this.loginForm.password = this.password
          console.log(this.loginForm)
          this.flsuhImg()
          console.log('error submit!!')
          return false
        }
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}


</style>

<style lang="scss" scoped>
.success_bottom{
  border-bottom: 1px solid #67c23a !important;
}
.el-input__suffix i{
  color: #67c23a !important;
}
.el-form-item__error{
  padding-left: 12px
}
.el-form-item__content{
  margin-left: 0px;
  padding-bottom: 20px;
}
.login-code{
  height: 40px;
}
.login-code img{
  width: 100%; height: 100%;
}
input{
  outline: none
}
input:-webkit-autofill {
    -webkit-box-shadow: 0 0 0px 1000px white inset !important;
}

.login-container {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    position: relative;
    width: 100%;
    height: 100%;
    margin: 0 auto;
    background: url('../../assets/cloud.jpg') 0 bottom repeat-x #049ec4;
    -webkit-animation: animate-cloud 20s linear infinite;
    animation: animate-cloud 20s linear infinite;
}
.login-weaper {
  margin: 0 auto;
  width:  1000px;
}
.login-left {
    min-height: 500px;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    border-top-left-radius: 5px;
    border-bottom-left-radius: 5px;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    -ms-flex-direction: column;
    flex-direction: column;
    background-color: #525252;
    color: #fff;
    float: left;
    width: 50%;
    position: relative;
}

.login-border {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border-left: none;
    border-top-right-radius: 5px;
    border-bottom-right-radius: 5px;
    color: #fff;
    background-color: #fff;
    width: 50%;
    float: left;
    position: relative;
    min-height: 500px;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
}
.login-main {
    margin: 0 auto;
    width: 65%;
    box-sizing: border-box;
}
.login-form {
    margin: 10px 0;
}
.login-form .el-form-item {
    margin-bottom: 12px;
}
.login-form .el-input input {
    padding-bottom: 10px;
    text-indent: 5px;
    background: transparent;
    border: none;
    border-radius: 0;
    color: #333;
    border-bottom: 1px solid #ebedf2;
}
.login-form .el-form-item__content {
    width: 100%;
}
.login-title {
    color: #333;
    margin-bottom: 40px;
    font-weight: 500;
    font-size: 22px;
    text-align: center;
    letter-spacing: 4px;
}
.el-input--small {
    font-size: 13px;
}
.el-input {
    position: relative;
    font-size: 14px;
    display: inline-block;
    width: 100%;
}
.el-input__prefix {
    left: 5px;
    transition: all .3s;
}
.el-input__prefix, .el-input__suffix {
    position: absolute;
    top: 0;
    -webkit-transition: all .3s;
    height: 100%;
    color: #333;
    text-align: center;
}
.login-form .el-input .el-input__prefix i {
    padding: 0 5px;
    font-size: 16px!important;
}
.login-form i {
    color: #333;
}
.el-input__suffix {
    right: 5px;
    transition: all .3s;
}
.el-input__prefix, .el-input__suffix {
    position: absolute;
    top: 0;
    -webkit-transition: all .3s;
    height: 100%;
    color: #c0c4cc;
    text-align: center;
}
.el-input__suffix, .el-tree.is-dragging .el-tree-node__content * {
    pointer-events: none;
}
.el-form-item{
width: 100%;
}
.el-button{
  width: 100%;
  background:#525252
}
.el-button span{
  color: #fff;
}
@keyframes  animate-cloud {
  0% {
      background-position: 600px 100%;
  }
  100% {
      background-position: 0 100%;
  }
}


</style>
