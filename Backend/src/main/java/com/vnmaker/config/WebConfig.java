package com.vnmaker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${vn.resource.path}")
    private String resourcePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:///" + resourcePath.replace("\\", "/"));
        
        // 添加Assets目录的资源处理器
        registry.addResourceHandler("/Assets/**")
                .addResourceLocations("file:///D:/visual novel/XG-Visual-Novel-Maker/Assets/");
    }
}