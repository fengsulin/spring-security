package com.lin.security.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MybatisPlusGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://192.168.2.131:3306/db_2022_cloud?useSSL=false"
                , "root"
                , "123456")
                .globalConfig(builder -> {
                    builder.author("fengsulin") // 设置作者
                            .outputDir("D:\\Projects\\ServerProjects\\spring-security\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.lin") // 设置父包名
                            .moduleName("security") // 设置父包模块名
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\Projects\\ServerProjects\\spring-security\\src\\main\\resources\\mappers")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(getTables("sys_menu")) // 设置需要生成的表名(all)
                    ; // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
