package com.lin.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置类
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 允许跨域的路径
                .allowedOriginPatterns("*") // 允许跨域请求的域名
                .allowCredentials(true) // 是否允许cookie
                .allowedMethods("GET","POST","DELETE","PUT","OPTIONS")    // 请求方式
                .allowedHeaders("*")    // 设置允许的headers属性
                .maxAge(3600);  // 跨域允许的时间
    }
}
