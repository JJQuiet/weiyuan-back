package com.ujn.spu.core;

import com.ujn.spu.utils.MannulOper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import com.ujn.spu.core.WsClientOper;
import com.ujn.spu.utils.MannulOper;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端。
 */
@Component
@ServerEndpoint("/api/websocket")
public class WebSocketManager {

    private static final CopyOnWriteArraySet<WebSocketManager> webSocketManagerSet = new CopyOnWriteArraySet<WebSocketManager>();
    /**
     * 与某个客户端的来连接绘画，需要通过它来给客户端发送数据
     */

    @Autowired
    MannulOper mannulOper;

    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        webSocketManagerSet.add(this);

        if (webSocketManagerSet.size()==1) {
            System.out.println("去连接到服务器的wsocket..........");
            String ret=mannulOper.getTest("open");
            if (ret.indexOf("成功")>0) {
                System.out.println("ret Suc:"+ret);
                sendMessage(ret);
            } else {
                System.out.println("ret Fail:"+ret);
                sendMessage(ret);
            }

        }
    }

    @OnClose
    public void onClose() throws IOException {
        webSocketManagerSet.remove(this);
        if (webSocketManagerSet.size()==0) {
            System.out.println("去关闭到服务器的wsocket..........");
            String ret=mannulOper.getTest("close");
            if (ret.indexOf("成功")>0) {
                System.out.println("ret Suc:"+ret);
                sendMessage(ret);
            } else {
                System.out.println("ret Fail:"+ret);
                sendMessage(ret);
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        if (message != null){
            System.out.println("recv ws msg:"+message);
            //sendMessage(message);
        }
    }

    @OnError
    public void onError(Throwable error){
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendMessageToClient(String message) throws IOException {
        System.out.println("snd msg:"+message);
        for (WebSocketManager webSocketManager : webSocketManagerSet) {
            webSocketManager.sendMessage(message);
        }
    }

}
