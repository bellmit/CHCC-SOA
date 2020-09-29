package com.guihe.platform.middle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guihe.platform.core.domain.QrtzLogs;
import com.guihe.platform.middle.service.QuartzLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author CHCC
 * @FileName QuartzLogsServiceImpl
 * @Date 2020/9/29 10:09 上午
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
@Component
public class QuartzLogsServiceImpl implements QuartzLogsService {

    @Override
    public IPage findList(QrtzLogs logs) {
        log.error("执行Quartz日志熔断器");
        return new Page(logs.getOffset(),logs.getLimmit());
    }
}
