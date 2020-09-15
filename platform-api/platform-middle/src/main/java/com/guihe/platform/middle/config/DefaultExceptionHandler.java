package com.guihe.platform.middle.config;

import com.guihe.platform.core.domain.Response;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Chengcheng on 2019/10/31.
 */
@RestControllerAdvice
public class DefaultExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public Response defaultExceptionHandler(HttpServletRequest req, Exception e){
        logger.error("暂无权限",e);
        return new Response.Builder(Response.ResponseCode.FAILURE.getNumber()).msg("暂无权限").build();
    }


}
