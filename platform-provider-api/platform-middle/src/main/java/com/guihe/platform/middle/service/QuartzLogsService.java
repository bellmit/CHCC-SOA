package com.guihe.platform.middle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guihe.platform.core.domain.QrtzLogs;
import com.guihe.platform.middle.service.impl.QuartzLogsServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author CHCC
 * @FileName QuartzLogsService
 * @Date 2020/9/29 10:09 上午
 * @Version 1.0
 * @Description TODO Quartz日志远程调用
 */
@FeignClient(value = "service-task",fallback = QuartzLogsServiceImpl.class)
public interface QuartzLogsService {

    @PostMapping("/service-quartz/logs/handle/list")
    Object findList(QrtzLogs logs);
}
