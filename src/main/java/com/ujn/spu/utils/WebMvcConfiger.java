package com.ujn.spu.utils;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcConfiger implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String mapsPath = "classpath:/static/临平离线地图/tiles_hybird";
        String imagesPath = "classpath:/static/static/images";
        registry.addResourceHandler("/baidumaps/**").addResourceLocations(mapsPath); //媒体资源
        registry.addResourceHandler("/bim/**").addResourceLocations(imagesPath); //媒体资源
    }
}




