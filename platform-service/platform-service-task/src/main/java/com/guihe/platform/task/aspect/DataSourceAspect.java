package com.guihe.platform.task.aspect;

import com.guihe.platform.core.aop.DataSource;
import com.guihe.platform.dao.dynamic.DynamicContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author CHCC
 * @FileName DataSourceAspect
 * @Date 2020/9/19 11:40 上午
 * @Version 1.0
 * @Description TODO 多数据源切面
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class DataSourceAspect {

    @Pointcut("@annotation(com.guihe.platform.core.aop.DataSource) " +
            "|| @within(com.guihe.platform.core.aop.DataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Class targetClass = point.getTarget().getClass();
        Method method = signature.getMethod();

        DataSource targetDataSource = (DataSource)targetClass.getAnnotation(DataSource.class);
        DataSource methodDataSource = method.getAnnotation(DataSource.class);
        if(targetDataSource != null || methodDataSource != null){
            String value;
            if(methodDataSource != null){
                value = methodDataSource.value().name();
            }else {
                value = targetDataSource.value().name();
            }
            DynamicContextHolder.setDataSourceType(value);
        }

        try {
            return point.proceed();
        } finally {
            DynamicContextHolder.clearDataSourceType();
            log.debug("销毁数据源");
        }
    }

}
