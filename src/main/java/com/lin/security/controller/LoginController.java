package com.lin.security.controller;

import com.lin.security.entity.SysUser;
import com.lin.security.service.LoginService;
import com.lin.security.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResultVo login(@RequestBody SysUser sysUser){
        return loginService.login(sysUser);
    }

    @DeleteMapping("/logout")
    public ResultVo logout(){
        return loginService.logout();
    }
}
