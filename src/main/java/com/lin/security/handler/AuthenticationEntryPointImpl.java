package com.lin.security.handler;

import com.lin.security.utils.WebUtils;
import com.lin.security.vo.ResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义spring-security认证异常处理
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 处理异常
        ResultVo error = ResultVo.error(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        WebUtils.renderString(response,error);
    }
}
