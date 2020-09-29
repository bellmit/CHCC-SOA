package com.guihe.platform.task.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guihe.platform.core.domain.QrtzLogs;
import com.guihe.platform.task.service.QrtzLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author CHCC
 * @FileName QuartzLogController
 * @Date 2020/9/29 10:03 上午
 * @Version 1.0
 * @Description TODO 定时器日志
 */
@Slf4j
@RestController
@RequestMapping("/service-quartz/logs/handle")
public class QuartzLogController {

    @Resource
    private QrtzLogsService qrtzLogsService;

    /**
     * @Description TODO 获取日志列表
     * @Author CHCC
     * @Date 2020/9/29 10:13 上午
     * @params  * @param logs
     * @returen com.baomidou.mybatisplus.core.metadata.IPage<com.guihe.platform.core.domain.QrtzLogs>
     **/
    @PostMapping("/list")
    public IPage list(@RequestBody QrtzLogs logs){
        return qrtzLogsService.findList(logs);
    }

}
