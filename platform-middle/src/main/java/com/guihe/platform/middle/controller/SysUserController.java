package com.guihe.platform.middle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.zxing.WriterException;
import com.guihe.platform.core.domain.Response;
import com.guihe.platform.core.domain.SysUser;
import com.guihe.platform.core.form.SysUserForm;
import com.guihe.platform.core.form.TreeForm;
import com.guihe.platform.core.result.SysUserResult;
import com.guihe.platform.middle.config.GoogleGenerator;
import com.guihe.platform.middle.service.SysUserService;
import com.guihe.platform.middle.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Chengcheng on 2019/4/11.
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/user")
@Api(value = "SysUserController",tags = "后台系统用户管理")
public class SysUserController extends BaseController{

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/list")
    //@RequiresPermissions("api:sys:user:list")
    @ApiOperation(value = "后台系统用户列表", notes = "有分页参数 offset limmit",consumes="application/x-www-form-urlencoded", response = SysUserResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "用户昵称 精确查询", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "1：启用 2：禁用", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "创建开始时间 格式：2019-01-01 12:59:59", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "创建结束时间 格式：2019-01-01 12:59:59", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "取第几页，范围：1~100000。默认1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limmit", value = "分页大小，范围：10~100。默认10", dataType = "int", paramType = "query")
    })
    public Response findList(SysUserForm sysUserForm){
        try {
            IPage<SysUserResult> list = sysUserService.findList(sysUserForm);
            return this.response(Response.ResponseCode.SUCCESS).data(list);
        } catch (Exception e){
            log.error("获取用户列表异常 {}",e);
            return this.response(Response.ResponseCode.FAILURE).message("获取用户列表异常,请联系管理员");
        }
    }

    @GetMapping("/generator/{id}")
    @ApiOperation(value = "生成google验证器二维码", notes = "生成google验证器二维码", response = String.class)
    public void generator(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException, WriterException {
        ServletOutputStream outputStream = null;
        try {
            SysUser sysUser = sysUserService.findInfo(id);
            String codeData = GoogleGenerator.createGoogleAuthQRCodeData(sysUser.getSecret(), sysUser.getUsername(), "后台管理ERP");
            outputStream = response.getOutputStream();
            // 设置响应的类型格式为图片格式
            response.setContentType("image/jpeg");
            //禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            GoogleGenerator.writeToStream(codeData,outputStream,200,200);
        }finally {
            if(outputStream != null){
                outputStream.close();
            }
        }
    }

    @PostMapping("/commitRole")
    //@RequiresPermissions("api:sys:user:commitRole")
    @ApiOperation(value = "提交权限数据", notes = "提交权限数据 给该用户分配权限 提交权限id",consumes="application/x-www-form-urlencoded", response = Response.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "权限id字符串 通过,分割", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "提交用户id", required = true, dataType = "int", paramType = "query")
    })
    public Response commitRole(TreeForm form){
        try {
            if(StringUtils.isNotBlank(form.getIds()) && form.getId() != null){
                sysUserService.commitRole(form.getId(),form.getIds());
            }
        }catch (Exception e){
            log.error("提交权限异常 {}", e);
            return this.response(Response.ResponseCode.FAILURE).message("提交权限异常, 请联系管理员");
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @PostMapping("/commitUser")
    @ApiOperation(value = "提交用户信息数据", notes = "修改用户信息  新增用户都走这个接口",consumes="application/x-www-form-urlencoded", response = Response.class)
    public Response commitUser(SysUser sysUser){
        if(sysUser.getId() == null){
            if(StringUtils.isBlank(sysUser.getPassword())){
                log.warn("密码为空");
                return this.response(Response.ResponseCode.FAILURE).message("密码为空");
            }
            if(StringUtils.isBlank(sysUser.getUsername())){
                log.warn("用户名为空");
                return this.response(Response.ResponseCode.FAILURE).message("用户名为空");
            }
            String password = sysUser.getPassword();
            String salt = RandomStringUtils.randomAlphanumeric(20);
            String sha256Password = ShiroUtils.sha256(password, salt);
            sysUser.setSalt(salt);
            sysUser.setPassword(sha256Password);
            sysUser.setSecret(GoogleGenerator.createSecretKey());
        }
        if(StringUtils.isBlank(sysUser.getNickname())){
            log.warn("用户昵称为空");
            return this.response(Response.ResponseCode.FAILURE).message("用户昵称为空");
        }
        if(StringUtils.isBlank(sysUser.getMobile())){
            log.warn("手机号为空");
            return this.response(Response.ResponseCode.FAILURE).message("手机号为空");
        }
        if(sysUser.getId() == null){
            sysUser.setCreateTime(new Date());
            sysUserService.createUser(sysUser);
        }else{
            sysUser.setUpdateTime(new Date());
            sysUserService.updateUser(sysUser);
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @PostMapping("/findRoles/{id}")
    @ApiOperation(value = "获取该用户所拥有的权限id",consumes="application/x-www-form-urlencoded", response = Response.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", paramType = "query")
    public Response findRoles(@PathVariable("id") Integer id){
        Object data = sysUserService.findRoles(id);
        return this.response(Response.ResponseCode.SUCCESS).data(data);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "获取用户信息",consumes="application/x-www-form-urlencoded", response = SysUser.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", paramType = "query")
    public Response findInfo(@PathVariable("id") Integer id){
        Object data = sysUserService.findInfo(id);
        return this.response(Response.ResponseCode.SUCCESS).data(data);
    }

    @PostMapping("/enable/{id}")
    //@RequiresPermissions("api:sys:user:enable")
    @ApiOperation(value = "启用用户",consumes="application/x-www-form-urlencoded", response = Response.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", paramType = "query")
    public Response enable(@PathVariable("id") String id){
        if(StringUtils.isNotBlank(id)){
            List<String> list = Arrays.asList(id.split(","));
            List<SysUser> users = new ArrayList<>(list.size());
            list.forEach(value -> {
                SysUser user = new SysUser();
                user.setId(Integer.parseInt(value));
                user.setStatus(1);
                users.add(user);
            });
            sysUserService.updateUser(users);
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @PostMapping("/disable/{id}")
   // @RequiresPermissions("api:sys:user:disable")
    @ApiOperation(value = "禁用用户",consumes="application/x-www-form-urlencoded", response = Response.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", paramType = "query")
    public Response disable(@PathVariable("id") String id){
        if(StringUtils.isNotBlank(id)){
            List<String> list = Arrays.asList(id.split(","));
            List<SysUser> users = new ArrayList<>(list.size());
            list.forEach(value -> {
                SysUser user = new SysUser();
                user.setId(Integer.parseInt(value));
                user.setStatus(2);
                users.add(user);
            });
            sysUserService.updateUser(users);
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @PostMapping("/reset/{id}")
    //@RequiresPermissions("api:sys:user:reset")
    @ApiOperation(value = "重置密码", notes = "重置密码为 12345678", consumes="application/x-www-form-urlencoded", response = Response.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", paramType = "query")
    public Response reset(@PathVariable("id") String id){
        if(StringUtils.isNotBlank(id)){
            List<String> list = Arrays.asList(id.split(","));
            List<SysUser> users = new ArrayList<>(list.size());
            list.forEach(value -> {
                SysUser user = new SysUser();
                user.setId(Integer.parseInt(value));
                String salt = RandomStringUtils.randomAlphanumeric(20);
                String sha256Password = ShiroUtils.sha256("12345678", salt);
                user.setSalt(salt);
                user.setPassword(sha256Password);
                users.add(user);
            });
            sysUserService.updateUser(users);
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

}
