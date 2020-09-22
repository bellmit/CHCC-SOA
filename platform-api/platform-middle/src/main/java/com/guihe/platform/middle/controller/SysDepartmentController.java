package com.guihe.platform.middle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guihe.platform.core.aop.MyLog;
import com.guihe.platform.core.domain.Response;
import com.guihe.platform.core.domain.SysDepartment;
import com.guihe.platform.core.domain.SysUser;
import com.guihe.platform.middle.service.SysDepartmentService;
import com.guihe.platform.middle.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author CHCC
 * @FileName SysDepartmentController
 * @Date 2020/9/22 9:36 上午
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/department")
@Api(value = "SysDepartmentController",tags = "系统部门管理")
public class SysDepartmentController extends BaseController {

    @Resource
    private SysDepartmentService sysDepartmentService;

    @MyLog(info = "系统部门管理列表")
    @PostMapping("/list")
    @ApiOperation(value = "列表", notes = "有分页参数 offset limmit",consumes="application/json", response = SysDepartment.class)
    public Response list(@RequestBody SysDepartment department){
        IPage<SysDepartment> page = sysDepartmentService.findList(department);
        sysDepartmentService.findChildren(page.getRecords());
        return this.response(Response.ResponseCode.SUCCESS).data(page);
    }

    @MyLog(info = "系统部门管理获取详情")
    @PostMapping("/detail/{id}")
    @ApiOperation(value = "根据id查询详情", notes = "根据id查询详情",consumes="application/json", response = SysDepartment.class)
    public Response findById(@PathVariable("id") Integer id){
        SysDepartment department = sysDepartmentService.getById(id);
        return this.response(Response.ResponseCode.SUCCESS).data(department);
    }

    @MyLog(info = "系统部门管理提交数据")
    @PostMapping("/commit")
    @ApiOperation(value = "提交数据", notes = "提交数据",consumes="application/json", response = Response.class)
    public Response commit(@RequestBody SysDepartment department){
        SysUser user = ShiroUtils.getUserEntity();
        if(department.getId() == null){
            department.setCreateBy(user.getNickname());
            department.setCreateTime(new Date());
            sysDepartmentService.save(department);
        }else{
            department.setUpdateBy(user.getNickname());
            department.setUpdateTime(new Date());
            sysDepartmentService.updateById(department);
        }
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @MyLog(info = "系统部门用户列表")
    @PostMapping("/findUser/{id}")
    @ApiOperation(value = "系统部门用户列表", notes = "系统部门用户列表",consumes="application/json", response = HashMap.class)
    public Response findUser(@PathVariable("id") Integer id){
        Map map = sysDepartmentService.findUser(id);
        return this.response(Response.ResponseCode.SUCCESS).data(map);
    }

    @MyLog(info = "系统部门分配用户")
    @PostMapping("/commitUser")
    @ApiOperation(value = "系统部门分配用户", notes = "系统部门分配用户",consumes="application/json", response = Response.class)
    public Response commitUser(@RequestBody Map<String,Object> map){
        if(Objects.isNull(map.get("id"))){
            return this.response(Response.ResponseCode.FAILURE).message("请重新打开选择器,未检测到部门id");
        }
        sysDepartmentService.commitUser((Integer)map.get("id"),map.get("ids") instanceof Collection? new ArrayList<>((Collection)map.get("ids")): null);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @MyLog(info = "系统部门更改状态")
    @PostMapping("/changeStatus")
    @ApiOperation(value = "系统部门更改状态", notes = "系统部门更改状态",consumes="application/json", response = Response.class)
    public Response changeStatus(@RequestBody SysDepartment department){
        SysUser user = ShiroUtils.getUserEntity();
        department.setUpdateBy(user.getNickname());
        department.setUpdateTime(new Date());
        department.setStatus(department.getStatus());
        sysDepartmentService.updateById(department);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @MyLog(info = "系统部门删除")
    @PostMapping("/delete")
    @ApiOperation(value = "系统部门删除", notes = "系统部门删除",consumes="application/json", response = Response.class)
    public Response delete(@RequestBody SysDepartment department){
        SysUser user = ShiroUtils.getUserEntity();
        department.setUpdateBy(user.getNickname());
        department.setUpdateTime(new Date());
        department.setStatus(3);
        sysDepartmentService.updateById(department);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

}
