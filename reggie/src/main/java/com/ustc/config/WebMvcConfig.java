package com.ustc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    // 配置类  主要是为了改写MVC框架的静态资源映射

    /**
     * 设置静态资源映射  使得可以不通过static 就可以访问资源
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源映射...");// 使用slf4j注解  打印日志
//        super.addResourceHandlers(registry);
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");// backend静态资源映射
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");// backend静态资源映射

    }
}
