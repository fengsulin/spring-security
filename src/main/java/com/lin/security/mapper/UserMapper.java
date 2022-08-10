package com.lin.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
