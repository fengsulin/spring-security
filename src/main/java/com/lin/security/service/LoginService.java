package com.lin.security.service;

import com.lin.security.entity.SysUser;
import com.lin.security.vo.ResultVo;

public interface LoginService {
    public ResultVo login(SysUser sysUser);

    ResultVo logout();
}
