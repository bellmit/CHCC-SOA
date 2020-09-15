package com.guihe.platform.middle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guihe.platform.core.domain.Response;
import com.guihe.platform.core.domain.SysMenu;
import com.guihe.platform.core.form.SysMenuForm;
import com.guihe.platform.core.result.MenuTreeResult;
import com.guihe.platform.core.result.SysMenuResult;
import com.guihe.platform.middle.service.SysMenuService;
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
 * Created by Chengcheng on 2019/4/11.
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/menu")
@Api(value = "SysMenuController",tags = "系统菜单管理")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 上午11:41
     */
    @PostMapping("/list")
    //@RequiresPermissions("api:sys:menu:list")
    @ApiOperation(value = "后台系统菜单列表", notes = "有分页参数 offset limmit",consumes="application/x-www-form-urlencoded", response = SysMenuResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "菜单名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "取第几页，范围：1~100000。默认1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limmit", value = "分页大小，范围：10~100。默认10", dataType = "int", paramType = "query")
    })
    public Response list(@RequestBody SysMenuForm form){
        try{
            IPage<SysMenuResult> list = sysMenuService.findList(form);
            return this.response(Response.ResponseCode.SUCCESS).data(list);
        } catch (Exception e){
            log.error("菜单列表异常 {}", e);
            return this.response(Response.ResponseCode.FAILURE).message("菜单列表异常, 请联系管理员");
        }
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 上午11:41
     */
    @PostMapping("/tree")
    @ApiOperation(value = "菜单树", notes = "菜单树结构",consumes="application/x-www-form-urlencoded", response = MenuTreeResult.class)
    public Response tree(){
        try {
            Object tree = sysMenuService.findTree();
            return this.response(Response.ResponseCode.SUCCESS).data(tree);
        } catch (Exception e){
            log.error("获取菜单树异常 {}",e);
            return this.response(Response.ResponseCode.FAILURE).message("获取菜单树异常, 请联系管理员");
        }
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 上午11:41
     */
    @PostMapping("/menus")
    @ApiOperation(value = "全部菜单集合", notes = "全部菜单集合",consumes="application/x-www-form-urlencoded", response = SysMenu.class)
    public Response menus(){
        try {
            Object menus = sysMenuService.findMenu();
            return this.response(Response.ResponseCode.SUCCESS).data(menus);
        } catch (Exception e){
            log.error("获取菜单树异常 {}",e);
            return this.response(Response.ResponseCode.FAILURE).message("获取父级菜单异常, 请联系管理员");
        }
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 上午11:41
     */
    @PostMapping("/add")
   // @RequiresPermissions("api:sys:menu:add")
    @ApiOperation(value = "添加菜单", notes = "添加菜单",consumes="application/x-www-form-urlencoded", response = Response.class)
    public Response addMenu(@RequestBody SysMenu sysMenu){
        if(StringUtils.isBlank(sysMenu.getName())){
            log.error("菜单名称为空");
            return this.response(Response.ResponseCode.FAILURE).message("菜单名称为空");
        }
        if(StringUtils.isBlank(sysMenu.getPerms())){
            log.error("菜单授权码为空");
            return this.response(Response.ResponseCode.FAILURE).message("菜单授权码为空");
        }
        if(sysMenu.getParentId() == null){
            sysMenu.setParentId(0);
        }
        try{
            sysMenuService.addMenu(sysMenu);
        }catch (Exception e){
            log.error("菜单添加异常 {}", e);
            return this.response(Response.ResponseCode.FAILURE).message("菜单添加异常, 请联系管理员");
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 上午11:41
     */
    @PostMapping("/edit")
   // @RequiresPermissions("api:sys:menu:edit")
    @ApiOperation(value = "编辑菜单", notes = "编辑菜单",consumes="application/x-www-form-urlencoded", response = Response.class)
    public Response editMenu(@RequestBody SysMenu sysMenu){
        if(sysMenu.getId() == null){
            log.error("请重新选择一条数据");
            return this.response(Response.ResponseCode.FAILURE).message("请重新选择一条数据");
        }
        try{
            sysMenuService.editMenu(sysMenu);
        }catch (Exception e){
            log.error("菜单编辑异常 {}", e);
            return this.response(Response.ResponseCode.FAILURE).message("菜单编辑异常, 请联系管理员");
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 上午11:41
     */
    @PostMapping("/detail/{id}")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "int", paramType = "query")
    @ApiOperation(value = "菜单详情", notes = "菜单详情",consumes="application/x-www-form-urlencoded", response = Response.class)
    public Response detailMenu(@PathVariable("id") Integer id){
        SysMenu sysMenu = sysMenuService.detailMenu(id);
        return this.response(Response.ResponseCode.SUCCESS).data(sysMenu);
    }

    /**
     * @Author Chengcheng
     * @Description :
     * @Date 2019/4/25 上午11:41
     */
    @PostMapping("/remove/{id}")
    //@RequiresPermissions("api:sys:menu:remove")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "int", paramType = "query")
    @ApiOperation(value = "删除菜单", notes = "删除菜单",consumes="application/x-www-form-urlencoded", response = Response.class)
    public Response removeMenu(@PathVariable("id") Integer id){
        List<SysMenu> list = sysMenuService.findChildrenById(id);
        if(list.isEmpty()){
            sysMenuService.removeMenu(id);
            return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
        }
        return this.response(Response.ResponseCode.FAILURE).message("请先删除子级菜单");
    }

}
