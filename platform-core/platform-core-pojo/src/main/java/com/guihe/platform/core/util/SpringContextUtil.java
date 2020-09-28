package com.guihe.platform.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author CHCC
 * @FileName SpringContextUtil
 * @Date 2020/9/27 4:07 下午
 * @Version 1.0
 * @Description TODO Springboot上下文对象
 */
public class SpringContextUtil {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name,clazz);
    }
}
