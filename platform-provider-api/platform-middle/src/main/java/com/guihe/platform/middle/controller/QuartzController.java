package com.guihe.platform.middle.controller;

import com.guihe.platform.core.aop.MyLog;
import com.guihe.platform.core.domain.QrtzJobDetails;
import com.guihe.platform.core.domain.Response;
import com.guihe.platform.core.form.BaseForm;
import com.guihe.platform.middle.service.QuartzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author CHCC
 * @FileName QuartzController
 * @Date 2020/9/26 2:39 下午
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
@RestController
@RequestMapping("/api/quartz")
@Api(value = "QuartzController",tags = "Quartz管理")
public class QuartzController extends BaseController {

    @Resource
    private QuartzService quartzService;

    @MyLog(info = "获取全部定时任务列表")
    @PostMapping("/list")
    @RequiresPermissions("api:quartz:list")
    @ApiOperation(value = "获取全部定时任务列表", notes = "获取全部定时任务列表", response = QrtzJobDetails.class)
    public Response findList(@RequestBody BaseForm form){
        Object quartzList = quartzService.findQuartzList(form);
        return this.response(Response.ResponseCode.SUCCESS).data(quartzList);
    }

    @MyLog(info = "获取当前运行中的任务列表")
    @PostMapping("/run/list")
    @RequiresPermissions("api:quartz:list")
    @ApiOperation(value = "获取当前运行中的任务列表", notes = "获取当前运行中的任务列表", response = QrtzJobDetails.class)
    public Response findRunList(){
        List quartzList = quartzService.findQuartzRunList();
        return this.response(Response.ResponseCode.SUCCESS).data(quartzList);
    }

    @MyLog(info = "删除任务")
    @PostMapping("/deleteJob")
    @RequiresPermissions("api:quartz:deleteJob")
    @ApiOperation(value = "删除任务", notes = "删除任务", response = Response.class)
    public Response deleteJob(@RequestBody QrtzJobDetails qrtzJobDetails){
        if(StringUtils.isBlank(qrtzJobDetails.getJobName())) return this.response(Response.ResponseCode.FAILURE).message("定时任务名称不能为空!");
        if(StringUtils.isBlank(qrtzJobDetails.getJobGroup())) return this.response(Response.ResponseCode.FAILURE).message("定时任务分组名称不能为空!");
        quartzService.deleteJob(qrtzJobDetails);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @MyLog(info = "暂停任务")
    @PostMapping("/pauseJob")
    @RequiresPermissions("api:quartz:pauseJob")
    @ApiOperation(value = "暂停任务", notes = "暂停任务", response = Response.class)
    public Response pauseJob(@RequestBody QrtzJobDetails qrtzJobDetails){
        if(StringUtils.isBlank(qrtzJobDetails.getJobName())) return this.response(Response.ResponseCode.FAILURE).message("定时任务名称不能为空!");
        if(StringUtils.isBlank(qrtzJobDetails.getJobGroup())) return this.response(Response.ResponseCode.FAILURE).message("定时任务分组名称不能为空!");
        quartzService.pauseJob(qrtzJobDetails);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @MyLog(info = "恢复任务")
    @PostMapping("/resumeJob")
    @RequiresPermissions("api:quartz:resumeJob")
    @ApiOperation(value = "恢复任务", notes = "恢复任务", response = Response.class)
    public Response resumeJob(@RequestBody QrtzJobDetails qrtzJobDetails){
        if(StringUtils.isBlank(qrtzJobDetails.getJobName())) return this.response(Response.ResponseCode.FAILURE).message("定时任务名称不能为空!");
        if(StringUtils.isBlank(qrtzJobDetails.getJobGroup())) return this.response(Response.ResponseCode.FAILURE).message("定时任务分组名称不能为空!");
        quartzService.resumeJob(qrtzJobDetails);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @MyLog(info = "立即执行任务")
    @PostMapping("/runJob")
    @RequiresPermissions("api:quartz:runJob")
    @ApiOperation(value = "立即执行任务", notes = "立即执行任务", response = Response.class)
    public Response runJob(@RequestBody QrtzJobDetails qrtzJobDetails){
        if(StringUtils.isBlank(qrtzJobDetails.getJobName())) return this.response(Response.ResponseCode.FAILURE).message("定时任务名称不能为空!");
        if(StringUtils.isBlank(qrtzJobDetails.getJobGroup())) return this.response(Response.ResponseCode.FAILURE).message("定时任务分组名称不能为空!");
        quartzService.runJob(qrtzJobDetails);
        return this.response(Response.ResponseCode.SUCCESS).message("操作成功");
    }

    @MyLog(info = "新增定时任务")
    @PostMapping("/addJob")
    @RequiresPermissions("api:quartz:addJob")
    @ApiOperation(value = "新增定时任务", notes = "新增定时任务", response = Response.class)
    public Response addJob(@RequestBody QrtzJobDetails qrtzJobDetails){
        if(StringUtils.isBlank(qrtzJobDetails.getJobName())) return this.response(Response.ResponseCode.FAILURE).message("定时任务名称不能为空!");
        if(StringUtils.isBlank(qrtzJobDetails.getJobGroup())) return this.response(Response.ResponseCode.FAILURE).message("定时任务分组名称不能为空!");
        if(StringUtils.isBlank(qrtzJobDetails.getJobTime())) return this.response(Response.ResponseCode.FAILURE).message("定时任务执行时间表达式不能为空!");
        quartzService.addJob(qrtzJobDetails);
        return this.response(Response.ResponseCode.SUCCESS).message("添加成功");
    }

    @MyLog(info = "修改定时任务执行时间")
    @PostMapping("/editJobTime")
    @RequiresPermissions("api:quartz:editJobTime")
    @ApiOperation(value = "修改定时任务执行时间", notes = "修改定时任务执行时间", response = Response.class)
    public Response editJobTime(@RequestBody QrtzJobDetails qrtzJobDetails){
        quartzService.editJobTime(qrtzJobDetails);
        return this.response(Response.ResponseCode.SUCCESS).message("修改成功");
    }

}
