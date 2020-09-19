package com.guihe.platform.middle.aspect;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.guihe.platform.core.aop.MyLog;
import com.guihe.platform.middle.util.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Chengcheng
 * @Description : 切面
 * @Date 2019/12/4 上午10:53
 */
@Slf4j
@Aspect
@Component
public class MyLogAspect {


    @Pointcut(value = "@annotation(com.guihe.platform.core.aop.MyLog)")
    private void pointcut() {}

    @Around(value = "pointcut() && @annotation(myLog)")
    public Object around(ProceedingJoinPoint point, MyLog myLog) {
        String info = myLog.info();
        log.info("==================================== 日志统计：方法描述 {} ====================================", info);
        MethodSignature signature = (MethodSignature) point.getSignature();
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        //请求的参数
        Object[] args = point.getArgs();
        try{
            String params = new Gson().toJson(args);
            log.info("执行方法：Class|方法名 {}|{}",className,methodName);
            log.info("请求参数 {}",params);

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ipAddr = IPUtil.getIpAddr(request);
            log.info("请求IP {}",ipAddr);

            String requestURI = request.getRequestURI();
            log.info("请求URL {}",requestURI);

            String method = request.getMethod();
            log.info("RequestMethod {}",method);
        }catch (Exception e){}

        try {
            return point.proceed(); //执行程序
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable.getMessage();
        }
    }

    @AfterReturning(value = "pointcut() && @annotation(myLog)", returning = "result")
    public Object afterReturning(JoinPoint point, MyLog myLog, Object result) {
        String jsonString = JSONObject.toJSONString(result);
        if(StringUtils.isNotBlank(jsonString) && jsonString.length() <= 200){
            log.info("请求返回信息 {}", jsonString);
        }
        log.info("==================================== 日志统计执行结束 ====================================");
        return result;
    }


    @AfterThrowing(value = "pointcut() && @annotation(myLog)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, MyLog myLog, Exception ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        log.info("请求：" + requestURI + " 出现异常");
    }

}
