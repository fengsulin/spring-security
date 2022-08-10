package com.lin.security.service.impl;

import com.lin.security.constants.SecurityConstant;
import com.lin.security.entity.User;
import com.lin.security.service.LoginService;
import com.lin.security.utils.JWTUtils;
import com.lin.security.utils.RedisUtils;
import com.lin.security.pojo.LoginUser;
import com.lin.security.vo.ResultVo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public ResultVo login(User user) {
        // 将登录信息封装成authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        // AuthenticationManager的authenticate方法进行认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果未通过，给出提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        // 认证通过，使用userid生成一个jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        Map<String ,Object> params = new HashMap<>();
        params.put("userId",userId);
        String jwtToken = JWTUtils.getJwtToken(params);
        // 将用户信息存入redis
        redisUtils.setCacheObject(SecurityConstant.REDIS_KEY_PREFIX+userId,loginUser);
        Map<String ,Object> map = new HashMap<>();
        map.put(SecurityConstant.AUTH, SecurityConstant.AUTH_PREFIX+" "+jwtToken);

        return ResultVo.success("登录成功",map);
    }

    @Override
    public ResultVo logout() {
        // 获取SecurityContextHolder中的用户Id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();
        // 删除redis中的用户信息
        redisUtils.deleteCacheObject(SecurityConstant.REDIS_KEY_PREFIX+id);
        return ResultVo.success("注销成功",null);
    }
}
