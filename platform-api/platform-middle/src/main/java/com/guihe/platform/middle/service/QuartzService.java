package com.guihe.platform.middle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guihe.platform.core.domain.QrtzJobDetails;
import com.guihe.platform.core.form.BaseForm;
import com.guihe.platform.middle.service.impl.QuartzServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/service-quartz/handle/run/list")
    List findQuartzRunList();

    @PostMapping("/service-quartz/handle/list")
    Object findQuartzList(BaseForm form);

    @PostMapping("/service-quartz/handle/addJob")
    void addJob(QrtzJobDetails qrtzJobDetails);

    @PostMapping("/service-quartz/handle/editJobTime")
    void editJobTime(QrtzJobDetails qrtzJobDetails);

    @PostMapping("/service-quartz/handle/deleteJob")
    void deleteJob(QrtzJobDetails qrtzJobDetails);

    @PostMapping("/service-quartz/handle/pauseJob")
    void pauseJob(QrtzJobDetails qrtzJobDetails);

    @PostMapping("/service-quartz/handle/resumeJob")
    void resumeJob(QrtzJobDetails qrtzJobDetails);

    @PostMapping("/service-quartz/handle/runJob")
    void runJob(QrtzJobDetails qrtzJobDetails);
}
