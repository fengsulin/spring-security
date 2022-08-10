package com.lin.security.controller;

import com.lin.security.entity.User;
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
    public ResultVo login(@RequestBody User user){
        return loginService.login(user);
    }

    @DeleteMapping("/logout")
    public ResultVo logout(){
        return loginService.logout();
    }
}
