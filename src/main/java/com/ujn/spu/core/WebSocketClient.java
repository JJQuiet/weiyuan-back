package com.ujn.spu.core;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.*;
import java.io.IOException;

import com.ujn.spu.domain.dto.RtLocDTO;
import com.ujn.spu.domain.entity.RtLocEntity;
import com.ujn.spu.service.RtLocService;
import com.ujn.spu.utils.MannulOper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@ClientEndpoint
public class WebSocketClient {
    @Autowired
    WebSocketManager webSocketManager;

    @Autowired
    MannulOper mannulOper;

    @Autowired
    private RtLocService rlService;
    public static WebSocketClient webSocketClient;

    @PostConstruct//用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
    public void init() {
        webSocketClient = this;
        webSocketClient.rlService=this.rlService;
    }

    //private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);

    /**
     * 这里使用静态，让 RedisUtils 属于类
     */
    // public static RedisUtils redisUtils;

    /**
     * session
     */
    private Session session;

    /**
     * <beforeInit>
     *
     * @throws
     */
    public static void beforeInit() {
        // 在socket配置类中调用此方法可以完成一些需要初始化注入的操作
    }

    @OnOpen
    public void onOpen(Session session) {
        //logger.info("连接开启...");
        System.out.println("长连接开启...");
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // redisUtils.set(UUID.randomUUID().toString().replaceAll("-", ""), message);
        //logger.info(message);
        System.out.println(message);
        if (webSocketManager==null) webSocketManager=new WebSocketManager();
        webSocketManager.sendMessageToClient(message);
        //写入数据库中
        if (message.indexOf("E")>0){
            RtLocDTO rlDTO=new RtLocDTO();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            rlDTO.setDatetime(formatter.format(date));
            //System.out.println(formatter.format(date));
            boolean validb=false;
            String[] infox = message.split(",");
            for(int i=0;i<infox.length;i++){
                if (infox[i].equals("N")&&((i+1)<infox.length)){
                    rlDTO.setLatitude(infox[(i+1)]);
                    validb=true;
                }
                else if (infox[i].equals("E")&&((i+1)<infox.length)){
                    rlDTO.setLongitude(infox[(i+1)]);
                }
                else if (infox[i].equals("V")&&((i+1)<infox.length)){
                    rlDTO.setVelocity(infox[(i+1)]);
                }
            }

            if (validb){
                System.out.println(rlDTO.getDatetime()+rlDTO.getLongitude()+rlDTO.getLatitude());
                if (webSocketClient.rlService==null) System.out.println("rlService is  NULLLLLLLLLLLLLL!!!!!!");
                else {
                    List<RtLocEntity> rlList=webSocketClient.rlService.listALLData();


                    if (rlList.isEmpty()) {
                        System.out.println("List nullllllllllllllllllllllllllllllll"+rlDTO.getDatetime()+rlDTO.getLongitude());
                        webSocketClient.rlService.createArticle(rlDTO);
                    }
                    else {
                        RtLocEntity rlEntity=rlList.get(0);
                        System.out.println("更新id:"+rlEntity.getId());
                        webSocketClient.rlService.updateById(rlEntity.getId().toString(),rlDTO);
                    }
                }
            }
        }
    }

    @OnClose
    public void onClose() {
        //logger.info("长连接关闭...");
        System.out.println("长连接关闭...");
        if (webSocketManager==null) webSocketManager=new WebSocketManager();
        try {
            webSocketManager.sendMessageToClient("ws已关闭");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        //logger.error("error, cause: ", t);
    }

    /**
     * <异步发送message>
     *
     * @param message message
     * @throws
     */
    public void send(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * <发送message>
     *
     * @param message message
     * @throws
     */
    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            //logger.error("error, cause: ", ex);
        }
    }

    /**
     * <关闭连接>
     *
     * @throws
     */
    public void close() throws IOException {
        if (this.session.isOpen()) {
            this.session.close();
        }
    }
}
