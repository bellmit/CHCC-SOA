package com.guihe.platform.core.aop;

import java.lang.annotation.*;

/**
 * @author CHCC
 * @FileName QuartzLog
 * @Date 2020/9/28 3:53 下午
 * @Version 1.0
 * @Description TODO 定时任务日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface QuartzLog {

    String value() default "";

}
