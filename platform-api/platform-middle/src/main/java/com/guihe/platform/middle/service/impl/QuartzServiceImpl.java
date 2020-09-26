package com.guihe.platform.middle.service.impl;

import com.guihe.platform.middle.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CHCC
 * @FileName QuartzServiceImpl
 * @Date 2020/9/26 2:59 下午
 * @Version 1.0
 * @Description TODO Quartz熔断器
 */
@Slf4j
@Component
public class QuartzServiceImpl implements QuartzService {

    @Override
    public List findQuartzList() {
        log.warn("已触发 Quartz熔断器 ...");
        return new ArrayList();
    }
}
