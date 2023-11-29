package com.ujn.spu.core;

import com.ujn.spu.core.AjaxResult;
import com.ujn.spu.core.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.websocket.*;
import java.net.URI;
import java.util.UUID;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WsClientOper {

    private static WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    private WebSocketClient client;
    private static Boolean isOk;
    private String uri = "ws://192.168.7.65:8080/api/websocket";
    private String ip = System.getProperty("wxfwq");

    /**
     * 打开设备
     *
     */
    public AjaxResult wsConnect() throws IOException {
        try {
            if (ip==null) {
                ip="192.168.7.65";
                System.out.println("服务器IP地址使用！****默认*****ip:" + ip);
            }
            else{
                System.out.println("服务器IP地址使用！****输入参数*****ip:" + ip);
            }
            uri="ws://"+ip+":8080/api/websocket";
            client = new WebSocketClient();
            WebSocketClient.beforeInit();
            container.connectToServer(client, new URI(uri));

            isOk = true;
            System.out.println("ws连接成功");
            return AjaxResult.success("ws连接成功");

        } catch (Exception e) {
            isOk = false;
            System.out.println("ws连接失败:"+ip);
            return AjaxResult.error("ws连接失败:"+ip);
        }
    }

    /**
     * <心跳检测 断线重连>
     *
     * @throws
     */
    private void heartbeatCheck()
    {
        //logger.info("[WebSocketConfig] start to heartbeatCheck");
        if (isOk != null && isOk) {
            try {
                client.send("ping " + ip);
            } catch (Exception e) {
                isOk = false;
            }
        } else {
            // 系统连接失败进行重试
            //logger.warn("系统连接失败，正在重连...");
            try {
                client.send("ping " + ip);
               // logger.warn("系统重连成功！");
                isOk = true;
            } catch (Exception e) {
                try {
                    client = new WebSocketClient();
                    container.connectToServer(client, new URI(uri));

                    isOk = true;
                }
                catch (Exception e1) {
                    isOk = false;
                }

                if (isOk != null && isOk) {
                    //logger.warn("系统重连成功！");
                }
            }
        }
    }

    /**
     *  关闭设备
     */
    public AjaxResult wsDisCnct() throws IOException {
        if (isOk) {
            try {
                client.close();
                isOk = false;
                System.out.println("ws关闭成功");
                return AjaxResult.success("ws关闭成功");
            }catch (Exception e) {
                System.out.println("ws关闭失败");
                return AjaxResult.error("ws关闭失败");
            }
        }
        else
            return AjaxResult.success("ws已经关闭");
    }

    /**
     * 发送信息
     */

}
