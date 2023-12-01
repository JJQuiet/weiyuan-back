package com.ujn.spu.utils;

import com.ujn.spu.config.GlobalData;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.timingwheel.SystemTimer;
import cn.hutool.cron.timingwheel.TimerTask;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Slf4j
public class MsgUnit {
    public static volatile CopyOnWriteArrayList<String> phoneList = new CopyOnWriteArrayList<String>();
    private static String  sendContent = "";

    private static String port = "";

    public static String  send(List<String> phoneArr, String content){
        if(MsgUnit.phoneList.size()>0)return "发送中，请稍后";
        MsgUnit.sendContent = content;
        HashMap paramMapOne = new HashMap();
        paramMapOne.put("username", GlobalData.webConf.getMsgUserName());
        paramMapOne.put("password",GlobalData.webConf.getMsgPassword());
        String spansurl = StrUtil.format("http://{}/api/sms/get_valid_spans",GlobalData.webConf.getMsgReqip());
        String spansRes = HttpRequest.post(spansurl).form(paramMapOne)
                .timeout(20000)//超时，毫秒
                .execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(spansRes);
        JSONArray spansArr = (JSONArray) jsonObject.get("data");
        String port = "";
        for (Object o : spansArr) {
            JSONObject jo = (JSONObject) o;
            if(jo.get("status").equals(1)){
                port = jo.get("port").toString();
                break;
            }
        }
        if(StrUtil.isEmpty(port))
        {
            return "设备未插卡";
        }
        MsgUnit.port = port;
        for (String s : phoneArr) {
            if(StrUtil.isEmpty(s))
                continue;
            phoneList.add(s);

        }
        log.info("发送开始");
        MsgUnit.sendOne();
        return "success";
    }
    public static Object getMsgLogs(String currentPage,String phone_number_filter){
        HashMap paramMap = new HashMap();
        paramMap.put("username",GlobalData.webConf.getMsgUserName());
        paramMap.put("password",GlobalData.webConf.getMsgPassword());
        paramMap.put("current_page",currentPage);
        if(StrUtil.isEmpty(phone_number_filter) == false)
        {
            paramMap.put("phone_number_filter",phone_number_filter);
        }
        String url = StrUtil.format("http://{}/api/smsoutbox/get",GlobalData.webConf.getMsgReqip());
        String  result2 = HttpRequest.post(url).form(paramMap)
                .timeout(20000)//超时，毫秒
                .execute().body();
        return result2;
    }
    private static void sendOne(){
        if(MsgUnit.phoneList.size() <=0)
        {
            log.info("发送完成");
            return;
        }
        String phone = MsgUnit.phoneList.remove(0);
        String msg = sendContent;
        HashMap paramMap = new HashMap();
        paramMap.put("username", GlobalData.webConf.getMsgUserName());
        paramMap.put("password",GlobalData.webConf.getMsgPassword());
        paramMap.put("spans[]",port);
        paramMap.put("msg",msg);
        paramMap.put("dest_num",phone);
        paramMap.put("mode","1");
        String url = StrUtil.format("http://{}/api/sms/send",GlobalData.webConf.getMsgReqip());
        HttpResponse result2 = HttpRequest.post(url).form(paramMap)
                .timeout(20000)//超时，毫秒
                .execute();
        log.info("发送中"+phone);
        SystemTimer systemTimer = new SystemTimer();
        systemTimer.start();
        systemTimer.addTask(new TimerTask(() -> MsgUnit.sendOne(), 8000));
    }
}
