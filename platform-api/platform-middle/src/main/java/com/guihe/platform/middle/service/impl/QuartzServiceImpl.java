package com.guihe.platform.middle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guihe.platform.core.domain.QrtzJobDetails;
import com.guihe.platform.core.form.BaseForm;
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
    public List findQuartzRunList() {
        log.warn("已触发 Quartz熔断器 ...");
        return new ArrayList();
    }

    @Override
    public IPage findQuartzList(BaseForm form) {
        log.warn("已触发 Quartz熔断器 ...");
        return new Page(form.getOffset(),form.getLimmit());
    }

    @Override
    public void addJob(QrtzJobDetails qrtzJobDetails) {
        log.warn("已触发 Quartz熔断器 ...");
    }

    @Override
    public void editJobTime(QrtzJobDetails qrtzJobDetails) {
        log.warn("已触发 Quartz熔断器 ...");
    }

    @Override
    public void deleteJob(QrtzJobDetails qrtzJobDetails) {
        log.warn("已触发 Quartz熔断器 ...");
    }

    @Override
    public void pauseJob(QrtzJobDetails qrtzJobDetails) {
        log.warn("已触发 Quartz熔断器 ...");
    }

    @Override
    public void resumeJob(QrtzJobDetails qrtzJobDetails) {
        log.warn("已触发 Quartz熔断器 ...");
    }

    @Override
    public void runJob(QrtzJobDetails qrtzJobDetails) {
        log.warn("已触发 Quartz熔断器 ...");
    }


}
