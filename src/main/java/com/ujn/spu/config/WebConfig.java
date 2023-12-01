package com.ujn.spu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String uploadDir = "uploads/"; // 上传照片的目录
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String mapsPath = "classpath:/static/临平离线地图/tiles_hybird";
        String imagesPath = "classpath:/static/static/images";
        registry.addResourceHandler("/baidumaps/**").addResourceLocations(mapsPath); //媒体资源
        registry.addResourceHandler("/bim/**").addResourceLocations(imagesPath); //媒体资源
        registry.addResourceHandler("uploads/**").addResourceLocations("file:" + new File(uploadDir).getAbsolutePath() + "/");

    }

}
