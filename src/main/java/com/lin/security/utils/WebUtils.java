package com.lin.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 将响应信息返回给客户端工具类
 */
public class WebUtils {

    public static <T> T renderString(HttpServletResponse response,T data){
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            String s = new ObjectMapper().writeValueAsString(data);
            response.getWriter().println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
