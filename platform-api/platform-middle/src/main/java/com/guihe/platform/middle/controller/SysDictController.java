package com.guihe.platform.middle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guihe.platform.core.aop.MyLog;
import com.guihe.platform.core.domain.*;
import com.guihe.platform.core.domain.SysDict;
import com.guihe.platform.middle.service.SysDictService;
import com.guihe.platform.middle.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author CHCC
 * @FileName SysDictController
 * @Date 2020/10/13 9:26 上午
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/dict")
@Api(value = "SysDictController",tags = "后台系统字典管理")
public class SysDictController extends BaseController {

    @Resource
    private SysDictService sysDictService;

    @MyLog(info = "系统字典管理列表")
    @PostMapping("/list")
    @RequiresPermissions("api:sys:dict:list")
    @ApiOperation(value = "列表", notes = "有分页参数 offset limmit",consumes="application/json", response = SysDict.class)
    public Response list(@RequestBody SysDict sysDict){
        IPage<SysDict> page = sysDictService.findList(sysDict);
        sysDictService.findChildren(page.getRecords());
        return this.response(Response.ResponseCode.SUCCESS).data(page);
    }

    @MyLog(info = "系统字典管理获取详情")
    @PostMapping("/detail/{id}")
    @RequiresPermissions("api:sys:dict:detail")
    @ApiOperation(value = "根据id查询详情", notes = "根据id查询详情",consumes="application/json", response = SysDict.class)
    public Response findById(@PathVariable("id") Integer id){
        SysDict sysDict = sysDictService.findById(id);
        return this.response(Response.ResponseCode.SUCCESS).data(sysDict);
    }

    @MyLog(info = "系统字典管理提交数据")
    @PostMapping("/commit")
    @RequiresPermissions("api:sys:dict:commit")
    @ApiOperation(value = "提交数据", notes = "提交数据",consumes="application/json", response = Response.class)
    public Response commit(@RequestBody SysDict sysDict){
        SysUser user = ShiroUtils.getUserEntity();
        if(sysDict.getId() == null){
            sysDict.setCreateBy(user.getNickname());
            sysDict.setCreateTime(new Date());
            sysDictService.saveDict(sysDict);
        }else{
            sysDict.setUpdateBy(user.getNickname());
            sysDict.setUpdateTime(new Date());
            sysDictService.updateDict(sysDict);
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @MyLog(info = "系统字典更改状态")
    @PostMapping("/changeStatus")
    @RequiresPermissions("api:sys:dict:changeStatus")
    @ApiOperation(value = "系统字典更改状态", notes = "系统字典更改状态",consumes="application/json", response = Response.class)
    public Response changeStatus(@RequestBody SysDict sysDict){
        SysUser user = ShiroUtils.getUserEntity();
        sysDict.setUpdateBy(user.getNickname());
        sysDict.setUpdateTime(new Date());
        sysDict.setStatus(sysDict.getStatus());
        sysDictService.updateById(sysDict);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @MyLog(info = "系统字典删除")
    @PostMapping("/delete")
    @RequiresPermissions("api:sys:dict:delete")
    @ApiOperation(value = "系统字典删除", notes = "系统字典删除",consumes="application/json", response = Response.class)
    public Response delete(@RequestBody SysDict sysDict){
        SysUser user = ShiroUtils.getUserEntity();
        sysDict.setUpdateBy(user.getNickname());
        sysDict.setUpdateTime(new Date());
        sysDict.setStatus(3);
        sysDictService.updateById(sysDict);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }
    
}



