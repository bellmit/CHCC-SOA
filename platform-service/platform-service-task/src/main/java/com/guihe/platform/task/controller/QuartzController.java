package com.guihe.platform.task.controller;

import com.guihe.platform.task.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author CHCC
 * @FileName QuartzController
 * @Date 2020/9/26 2:29 下午
 * @Version 1.0
 * @Description TODO Quartz远程调用控制器
 */
@Slf4j
@RestController
@RequestMapping("/service-quartz/handle")
public class QuartzController {

    @Resource
    private QuartzService quartzService;

    @GetMapping("/list")
    public List findQuartzList(){
        return quartzService.queryAllJob();
    }

}
