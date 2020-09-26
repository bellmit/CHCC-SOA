package com.guihe.platform.middle.service;

import com.guihe.platform.middle.service.impl.QuartzServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author CHCC
 * @FileName QuartzService
 * @Date 2020/9/26 2:36 下午
 * @Version 1.0
 * @Description TODO Quartz远程调用
 */
@FeignClient(value = "service-task",fallback = QuartzServiceImpl.class)
public interface QuartzService {

    @GetMapping("/service-quartz/handle/list")
    List findQuartzList();

}
