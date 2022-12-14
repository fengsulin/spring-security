package com.lin.security.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('system:test:list')")   // 带有test字符串的有权限访问该接口
    public String hello(){
        return "hello";
    }

    @GetMapping("/word")
    @PreAuthorize("@exr.hasAuthority('system:test:list')")  // SPEL表达式中使用@exr引用bean
    public String word(){
        return "word";
    }
}
