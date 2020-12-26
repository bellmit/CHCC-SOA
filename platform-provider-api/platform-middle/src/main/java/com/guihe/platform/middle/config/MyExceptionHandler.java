package com.guihe.platform.middle.config;

import com.guihe.platform.core.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author CHCC
 * @FileName ExceptionHandler
 * @Date 2020/9/22 9:54 上午
 * @Version 1.0
 * @Description TODO 异常处理器
 */
@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public Response unauthorizedException(UnauthorizedException e){
        log.error("没有权限执行该方法");
        return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("暂无权限,请联系管理员!").build();
    }

    @ExceptionHandler(Exception.class)
    public Response exceptionHandler(Exception e){
        log.error("处理未知异常:",e);
        return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("异常,请联系管理员!").build();
    }

}
