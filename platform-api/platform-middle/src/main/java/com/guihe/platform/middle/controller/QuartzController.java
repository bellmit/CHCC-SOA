package com.guihe.platform.middle.controller;

import com.guihe.platform.core.domain.Response;
import com.guihe.platform.middle.service.QuartzService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/list")
    public Response findList(){
        List quartzList = quartzService.findQuartzList();
        return this.response(Response.ResponseCode.SUCCESS).data(quartzList);
    }

}
