package com.guihe.platform.middle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guihe.platform.core.aop.MyLog;
import com.guihe.platform.core.domain.QrtzLogs;
import com.guihe.platform.core.domain.Response;
import com.guihe.platform.middle.service.QuartzLogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author CHCC
 * @FileName QuartzLogsController
 * @Date 2020/9/29 10:08 上午
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
@RestController
@RequestMapping("/api/quartz/logs")
@Api(value = "QuartzLogsController",tags = "Quartz日志")
public class QuartzLogsController extends BaseController {

    @Resource
    private QuartzLogsService quartzLogsService;

    @MyLog(info = "获取Quartz日志列表")
    @PostMapping("/list")
    @RequiresPermissions("api:quartz:log:list")
    @ApiOperation(value = "获取Quartz日志列表", notes = "获取Quartz日志列表", response = QrtzLogs.class)
    public Response findList(@RequestBody QrtzLogs logs){
        Object quartzList = quartzLogsService.findList(logs);
        return this.response(Response.ResponseCode.SUCCESS).data(quartzList);
    }

}
