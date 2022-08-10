package com.lin.security.service;

import com.lin.security.entity.User;
import com.lin.security.vo.ResultVo;

public interface LoginService {
    public ResultVo login(User user);

    ResultVo logout();
}
