package com.guihe.platform.core.aop;

import java.lang.annotation.*;

/**
 * @author CHCC
 * @FileName MyLog
 * @Date 2020/9/15 9:18 上午
 * @Version 1.0
 * @Description TODO 日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyLog {

    /**接口详情*/
    String info() default "";

}
