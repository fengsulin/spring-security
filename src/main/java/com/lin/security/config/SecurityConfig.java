package com.lin.security.config;

import com.lin.security.filter.JwtAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 开启基于注解的权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthTokenFilter jwtAuthTokenFilter;

    /**
     * 创建BCryptPasswordEncoder注入容器,可以用于明文加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// 关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 不通过session获取SecurityContext
                .and()
                .authorizeRequests()
                .antMatchers("/user/login","/hello").permitAll() // 放行登录接口
                .anyRequest().authenticated();

        // 配置自定义过滤器的顺序
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
