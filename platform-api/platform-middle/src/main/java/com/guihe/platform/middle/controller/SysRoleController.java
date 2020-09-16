package com.guihe.platform.middle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guihe.platform.core.domain.Response;
import com.guihe.platform.core.domain.SysRole;
import com.guihe.platform.core.form.SysRoleForm;
import com.guihe.platform.core.form.TreeForm;
import com.guihe.platform.core.result.SysRoleResult;
import com.guihe.platform.middle.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Chengcheng on 2019/4/25.
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/role")
@Api(value = "SysRoleController",tags = "后台系统角色管理")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 下午3:52
     */
    
    @PostMapping("/list")
   // @RequiresPermissions("api:sys:role:list")
    @ApiOperation(value = "后台系统角色列表", notes = "有分页参数 offset limmit",consumes="application/x-www-form-urlencoded", response = SysRoleResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "角色名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态 1：启用 2：禁用", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "取第几页，范围：1~100000。默认1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limmit", value = "分页大小，范围：10~100。默认10", dataType = "int", paramType = "query")
    })
    public Response list(SysRoleForm form){
        try{
            IPage<SysRoleResult> list = sysRoleService.findList(form);
            return this.response(Response.ResponseCode.SUCCESS).data(list);
        } catch (Exception e){
            log.error("角色列表异常 {}", e);
            return this.response(Response.ResponseCode.FAILURE).message("角色列表异常, 请联系管理员");
        }
    }

    @PostMapping("/getRoles")
    @ApiOperation(value = "获取全部权限集合", consumes="application/x-www-form-urlencoded", response = SysRole.class)
    public Response getRoles(){
        try{
            Object list = sysRoleService.getRoles();
            return this.response(Response.ResponseCode.SUCCESS).data(list);
        } catch (Exception e){
            log.error("获取角色列表异常 {}", e);
            return this.response(Response.ResponseCode.FAILURE).message("获取角色列表异常, 请联系管理员");
        }
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 下午3:52
     */
    
    @PostMapping("/commitTree")
    //@RequiresPermissions("api:sys:role:commitTree")
    @ApiOperation(value = "提交权限", notes = "给该权限分配菜单", consumes="application/x-www-form-urlencoded", response = Response.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "菜单id拼接 通过,分割", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "权限id", dataType = "int", paramType = "query")
    })
    public Response commitTree(TreeForm form){
        try {
            if(StringUtils.isNotBlank(form.getIds()) && form.getId() != null){
                sysRoleService.commitTree(form.getId(),form.getIds());
            }
        }catch (Exception e){
            log.error("提交权限异常 {}", e);
            return this.response(Response.ResponseCode.FAILURE).message("提交权限异常, 请联系管理员");
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 下午3:52
     */
    @PostMapping("/findTree/{id}")
    @ApiImplicitParam(name = "id", value = "角色id", dataType = "int", paramType = "query")
    @ApiOperation(value = "权限列表", notes = "通过角色id 获取所有的菜单id", consumes="application/x-www-form-urlencoded", response = Response.class)
    public Response findTree(@PathVariable("id") Integer id){
        try {
            Object data = sysRoleService.findTree(id);
            return this.response(Response.ResponseCode.SUCCESS).data(data);
        }catch (Exception e){
            log.error("获取权限列表异常 {}", e);
            return this.response(Response.ResponseCode.FAILURE).message("获取权限列表异常,请联系管理员");
        }
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 下午3:52
     */
    
    @PostMapping("/commit")
    //@RequiresPermissions("api:sys:role:commit")
    @ApiOperation(value = "提交角色信息", notes = "新增/修改 都是这个接口", consumes="application/x-www-form-urlencoded", response = Response.class)
    public Response commitRole(SysRole sysRole){
        try{
            sysRoleService.commitRole(sysRole);
        }catch (Exception e){
            log.error("提交角色信息异常 {}",e);
            return this.response(Response.ResponseCode.FAILURE).message("提交角色信息异常, 请联系管理员");
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 下午3:52
     */
    @PostMapping("/detail/{id}")
    //@RequiresPermissions("api:sys:role:detail")
    @ApiImplicitParam(name = "id", value = "角色id", dataType = "int", paramType = "query")
    @ApiOperation(value = "获取角色信息", consumes="application/x-www-form-urlencoded", response = Response.class)
    public Response findInfo(@PathVariable("id") Integer id){
        Object data = sysRoleService.findInfo(id);
        return this.response(Response.ResponseCode.SUCCESS).data(data);
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 下午3:52
     */
    @PostMapping("/enable/{id}")
    //@RequiresPermissions("api:sys:role:enable")
    @ApiImplicitParam(name = "id", value = "角色id", dataType = "int", paramType = "query")
    @ApiOperation(value = "启用角色", consumes="application/x-www-form-urlencoded", response = Response.class)
    public Response enable(@PathVariable("id") Integer id){
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        sysRole.setStatus(1);
        sysRoleService.enable(sysRole);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 下午3:52
     */
    @PostMapping("/disable/{id}")
    //@RequiresPermissions("api:sys:role:disable")
    @ApiImplicitParam(name = "id", value = "角色id", dataType = "int", paramType = "query")
    @ApiOperation(value = "禁用角色", consumes="application/x-www-form-urlencoded", response = Response.class)
    public Response disable(@PathVariable("id") Integer id){
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        sysRole.setStatus(2);
        sysRoleService.disable(sysRole);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

}
