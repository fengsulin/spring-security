package com.lin.security.expression;

import com.lin.security.pojo.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限校验
 */
@Component("exr")
public class MySecurityExpressionRoot {

    public boolean hasAuthority(String authority){
        // 获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        return permissions.contains(authority);
        // 判断用户权限集合中是否存在authority
    }
}
