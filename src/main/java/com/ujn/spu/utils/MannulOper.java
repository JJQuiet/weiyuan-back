package com.ujn.spu.utils;

import com.ujn.spu.core.AjaxResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MannulOper {

    public static String getTest(String apis){
        RestTemplate restTemplate = new RestTemplate();
        String url="http://127.0.0.1:8080/opt/ws/";
        System.out.println(url);
        String notice = restTemplate.getForObject(url+apis
                , String.class);
        System.out.println(notice);
        return notice;
    }
}