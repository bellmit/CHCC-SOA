package com.guihe.platform.core.aop;

import com.guihe.platform.core.enmus.DataSourceType;

import java.lang.annotation.*;

/**
 * @author CHCC
 * @FileName DataSource
 * @Date 2020/9/19 11:38 上午
 * @Version 1.0
 * @Description TODO
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

    DataSourceType value();

}
