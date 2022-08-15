package com.lin.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.security.constants.SecurityConstant;
import com.lin.security.utils.JWTUtils;
import com.lin.security.utils.RedisUtils;
import com.lin.security.pojo.LoginUser;
import com.lin.security.utils.WebUtils;
import com.lin.security.vo.ResultVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Objects;

/**
 * 这里采用继承OncePerRequestFilter，防止同一个请求被多次处理
 */

@Component
@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 浏览器预检机制
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)){
            filterChain.doFilter(request,response);
            return;
        }

        // 获取token
        String token = request.getHeader(SecurityConstant.AUTH);
        System.out.println("token为："+token);
        if(! StringUtils.hasText(token)){
            // 如果token不存在，则放行（放行后SpringSecurity有自己的拦截器进行拦截校验）
            filterChain.doFilter(request,response);
            return;
        }
        // 解析token
        String[] tokens = token.split(" ");
        String userId = null;
        try{
            Jws<Claims> decode = JWTUtils.decode(tokens[1]);
            userId = (String) decode.getBody().get("userId");
        }catch (Exception e){
            WebUtils.renderString(response,ResultVo.failed("token已过期"));
            log.error("token已过期");
            e.printStackTrace();
        }
        // 从redis获取用户信息
        String redisKey = SecurityConstant.REDIS_KEY_PREFIX+userId;
        LoginUser loginUser = redisUtils.getCacheObject(redisKey);
        // 存入SecurityContextHolder
        if(Objects.isNull(loginUser)){
            WebUtils.renderString(response,ResultVo.failed("用户信息异常"));
            log.warn("用户信息异常");
            throw new RuntimeException("用户信息异常");
        }
        // todo,获取权限信息封装给你到Authentication中
        Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request,response);

    }

}
