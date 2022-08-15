package com.lin.security;

import com.lin.security.entity.SysMenu;
import com.lin.security.mapper.SysMenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SpringSecurityTest {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Test
    void contextLoads() {
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    void getPermsTest(){
        List<String> perms = sysMenuMapper.selectPermsByUserId(1);
        System.out.println(perms.get(0));
    }

}
