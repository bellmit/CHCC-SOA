package com.guihe.platform.common.core.config;

import com.guihe.platform.core.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author CHCC
 * @FileName GlobalExceptionHandler
 * @Date 2020/12/28 11:17 上午
 * @Version 1.0
 * @Description TODO 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response illegalArgumentException(IllegalArgumentException e) {
        log.error("参数非法异常={}", e.getMessage(), e);
        return Response.builder(Response.ResponseCode.FAILURE).message(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response exception(Exception e) {
        log.info("保存全局异常信息 ex={}", e.getMessage(), e);
        return Response.builder(Response.ResponseCode.FAILURE).message(e.getMessage());
    }

}
