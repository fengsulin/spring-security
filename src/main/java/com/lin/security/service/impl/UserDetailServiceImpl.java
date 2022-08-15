package com.lin.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.security.entity.SysUser;
import com.lin.security.mapper.SysMenuMapper;
import com.lin.security.mapper.UserMapper;
import com.lin.security.pojo.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        if(Objects.isNull(sysUser)){
            throw new RuntimeException("用户名或密码错误");
        }
        // todo 查询数据库获取用户对应的权限
        List<String> permissions = sysMenuMapper.selectPermsByUserId(sysUser.getId());  // 暂时固定写死
        return new LoginUser(sysUser,permissions);
    }

}
