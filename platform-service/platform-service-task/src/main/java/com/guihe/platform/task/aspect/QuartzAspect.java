package com.guihe.platform.task.aspect;

import com.alibaba.fastjson.JSONObject;
import com.guihe.platform.core.aop.QuartzLog;
import com.guihe.platform.core.domain.QrtzLogs;
import com.guihe.platform.task.service.QrtzLogsService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author CHCC
 * @FileName QuartzAspect
 * @Date 2020/9/28 3:54 下午
 * @Version 1.0
 * @Description TODO 定时器切面
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class QuartzAspect {

    @Resource
    private QrtzLogsService qrtzLogsService;

    @Pointcut("@annotation(com.guihe.platform.core.aop.QuartzLog) " +
            "|| @within(com.guihe.platform.core.aop.QuartzLog)")
    public void pointcut() {}

    @Around(value = "pointcut() && @annotation(myLog)")
    public Object around(ProceedingJoinPoint point, QuartzLog myLog) {
        String name = myLog.value();
        log.info("-------------------------------------------------------------------------");
        log.info("↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ 执行定时任务: {} ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓", name);
        String className,methodName,description = "",jobName = "",jobGroup = "",jobData = null,errMsg = null;
        Double playTime = 0.00;
        Integer status = 1;
        long startTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) point.getSignature();
        className = point.getTarget().getClass().getName();
        methodName = signature.getName();
        try {
            //请求的参数
            Object[] args = point.getArgs();
            for (Object arg : args) {
                if(arg instanceof JobExecutionContext){
                    JobExecutionContext context = (JobExecutionContext) arg;
                    JobDetail jobDetail = context.getJobDetail();
                    JobKey key = jobDetail.getKey();
                    jobName = key.getName();
                    jobGroup = key.getGroup();
                    description = jobDetail.getDescription();
                    log.info("执行定时器详情：任务名|任务分组----{}|{}",jobName,jobGroup);
                    log.info("定时器说明: {}",description);
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    jobData = JSONObject.toJSONString(jobDataMap);
                    log.info("执行定时器所需数据: {}",jobData);
                }
            }
        }catch (Exception e){}
        log.info("执行方法：Class|方法名 {}|{}",className,methodName);
        try {
            Object proceed = point.proceed();
            long endTime = System.currentTimeMillis();
            playTime = (endTime - startTime) / 1000.00;
            log.info("总耗时: {}s",playTime);
            log.info("执行结束,无异常");
            return proceed; //执行程序
        } catch (Throwable throwable) {
            status = 2;
            errMsg = throwable.getMessage();
            long endTime = System.currentTimeMillis();
            playTime = (endTime - startTime) / 1000.00;
            log.info("总耗时: {}s",playTime);
            log.error("执行结束,出现异常",throwable);
            throwable.printStackTrace();
            return throwable.getMessage();
        }finally {
            QrtzLogs logs = new QrtzLogs(className, methodName, jobName, jobGroup, description, playTime, jobData, status, errMsg, new Date());
            qrtzLogsService.save(logs);
            log.info("↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ 执行定时任务结束. ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑");
        }
    }

}
