package com.guihe.platform.middle.controller;

import com.guihe.platform.core.aop.MyLog;
import com.guihe.platform.core.domain.Response;
import com.guihe.platform.core.domain.SysRole;
import com.guihe.platform.core.domain.SysUser;
import com.guihe.platform.core.result.LoginInfoResult;
import com.guihe.platform.core.util.CipherAESUtil;
import com.guihe.platform.middle.config.GoogleGenerator;
import com.guihe.platform.middle.service.SysMenuService;
import com.guihe.platform.middle.service.SysRoleService;
import com.guihe.platform.middle.service.SysUserService;
import com.guihe.platform.middle.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Chengcheng on 2019/12/18.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api(value = "LoginController",tags = "登录/获取当前用户权限信息")
public class LoginController extends BaseController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysMenuService sysMenuService;

    @MyLog(info = "用户登录")
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "描述：登录模块", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true,dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true,dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true,dataType = "string", paramType = "query")
    })
    public Response loginSubmit(@RequestBody Map<String,String> sysUser){
        try{
            try{
                if(StringUtils.isBlank(sysUser.get("username"))){
                    return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("账号为空").build();
                }
                if(StringUtils.isBlank(sysUser.get("password"))){
                    return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("密码为空").build();
                }
                SysUser user = sysUserService.findByUsername(sysUser.get("username"));
                if(user == null){
                    return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("用户名不存在").build();
                }
                String secret = user.getSecret();
                if(!GoogleGenerator.verify(secret,sysUser.get("code"))){
                    return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("验证码错误").build();
                }
                String decrypt = CipherAESUtil.desDecrypt(sysUser.get("password"));
                if(StringUtils.isBlank(decrypt)){
                    return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("密码解析失败").build();
                }
                sysUser.put("password",decrypt);
            }catch (Exception e){
                log.error("",e);
                return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("请重新操作，如果重复出现该问题请联系管理员!!").build();
            }
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(sysUser.get("username"), sysUser.get("password"));
            subject.login(token);
        }catch (IncorrectCredentialsException e) {
            return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("账号或密码不正确!").build();
        }catch (LockedAccountException e) {
            return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("账号已被锁定,请联系管理员!").build();
        }catch (AuthenticationException e) {
            return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("账户验证失败!").build();
        }
        return new Response.Builder(Response.ResponseCode.SUCCESS.getNumber()).build();
    }

    @PostMapping("/info")
    @ApiOperation(value = "获取角色权限信息", notes = "获取角色权限信息", response = LoginInfoResult.class)
    public Response info(){
        try {
            SysUser sysUser = ShiroUtils.getUserEntity();
            if(sysUser == null) return new Response.Builder(Response.ResponseCode.NEED_LOGIN.getNumber()).msg("获取用户信息失败").build();
            LoginInfoResult result = new LoginInfoResult();
            SysRole sysRole = sysRoleService.findByUserId(sysUser.getId());
            result.setMenus(sysMenuService.indexMenu(sysUser.getId()));
            result.setRoleName(sysRole == null? "暂无角色":sysRole.getName());
            result.setNickname(sysUser.getNickname());
            return this.response(Response.ResponseCode.SUCCESS).data(result);
        }catch (Exception e){
            log.error("异常", e);
            return this.response(Response.ResponseCode.FAILURE).message("异常, 请联系管理员");
        }
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录", response = String.class)
    public Response logout(){
        ShiroUtils.logout();
        return this.response(Response.ResponseCode.SUCCESS);
    }

}
