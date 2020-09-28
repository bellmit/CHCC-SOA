package com.guihe.platform.task.job;

import com.guihe.platform.core.aop.QuartzLog;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


/**
 * @author CHCC
 * @FileName MyJob
 * @Date 2020/9/26 2:17 下午
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
@Component(value = "myJob")
public class MyJob extends QuartzJobBean {

    @QuartzLog("myJob")
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取参数
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        // 业务逻辑 ...
        log.info("------springbootquartzonejob执行"+jobDataMap.get("name").toString()+"###############"+jobExecutionContext.getTrigger());
    }
}
