package com.guihe.platform.middle.controller;

import com.alibaba.fastjson.JSONObject;
import com.guihe.platform.core.domain.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Chengcheng on 2018/12/12.
 */
public abstract class BaseController {

    protected Response response(Response.ResponseCode code){
        return response(null,null,code);
    }

    protected Response response(String message,Response.ResponseCode code){
        return response(null,message,code);
    }

    protected Response response(Object data,Response.ResponseCode code){
        return new Response.Builder(code.getNumber()).data(data).build();
    }

    protected Response response(Object data,String message,Response.ResponseCode code){
        return new Response.Builder(code.getNumber()).data(data).msg(message).build();
    }

    protected void writeFailure(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        writer.write(JSONObject.toJSONString(this.response(Response.ResponseCode.FAILURE).message("无法导出，暂无订单数据")));
        writer.flush();
        writer.close();
    }

}
