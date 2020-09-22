package com.guihe.platform.task.config;

import com.guihe.platform.core.domain.Response;
import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler(Exception.class)
    public Response exceptionHandler(Exception e){
        log.error("处理未知异常:",e);
        return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("异常,请联系管理员!").build();
    }

}
