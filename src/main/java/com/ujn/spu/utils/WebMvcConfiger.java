package com.ujn.spu.utils;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcConfiger implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");

        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            registry.addResourceHandler("/baidumaps/**").addResourceLocations("file:D:/project/dongpingMap/"); //媒体资源
            registry.addResourceHandler("/bim/**").addResourceLocations("file:D:/webstatic/"); //媒体资源
        } else {  //linux 和mac
//            registry.addResourceHandler("/baidumaps/**").addResourceLocations("file:/webstatic/");   //媒体资源
            registry.addResourceHandler("/baidumaps/**").addResourceLocations("file:"+System.getProperty("user.dir")+"/map/");
            registry.addResourceHandler("/bim/**").addResourceLocations("file:/webstatic/");   //媒体资源
        }
    }
}




