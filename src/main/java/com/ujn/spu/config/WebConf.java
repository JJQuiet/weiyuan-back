package com.ujn.spu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="webconf")
@Configuration
@Data
public class WebConf {
    private String msgReqip;
    private String msgUserName;
    private String msgPassword;
}
