package com.guihe.platform.middle.shiro;

import com.alibaba.fastjson.JSONObject;
import com.guihe.platform.core.domain.Response;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CHCC
 * @FileName ShiroLoginFilter
 * @Date 2020/9/16 10:47 上午
 * @Version 1.0
 * @Description TODO
 */
public class ShiroLoginFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(JSONObject.toJSON(new Response.Builder(Response.ResponseCode.NEED_LOGIN.getNumber()).msg("请重新登录").build()).toString());
        return false;
    }

}
