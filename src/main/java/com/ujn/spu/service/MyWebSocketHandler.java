package com.ujn.spu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import com.ujn.spu.dao.CameraLocRepository;
import com.ujn.spu.domain.entity.CameraLocEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.*;



@Slf4j
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    private Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    @Autowired
    CameraLocRepository cameraLocRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 连接建立后的逻辑
        log.info("连接建立");
        sessions.add(session);
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理收到的文本消息
        log.info("收到消息：" + message.getPayload());
        updateCameraLocation(message.getPayload());
        broadcastMessageToOthers(session, message.getPayload());
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 连接关闭后的逻辑
        log.info("连接关闭");
        sessions.remove(session);
    }
    // ...可以重写更多方法来处理不同的事件...

    private void broadcastMessageToOthers(WebSocketSession sender, String message) {
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen() && !session.getId().equals(sender.getId())) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                log.error("Error during broadcasting message", e);
            }
        }
    }

    private void updateCameraLocation(String jsonMessage) {
        try {
            JSONObject json = new JSONObject(jsonMessage);
            String cameraName = json.getString("CameraName");
            String latVal = json.getString("LAT_VAL");
            String lngVal = json.getString("LNG_VAL");

            CameraLocEntity cameraLocation = cameraLocRepository.findByCameraName(cameraName)
                    .orElse(new CameraLocEntity());
            cameraLocation.setCameraName(cameraName);
            cameraLocation.setLatVal(latVal);
            cameraLocation.setLngVal(lngVal);
            cameraLocRepository.save(cameraLocation);
        } catch (JSONException e) {
            log.error("Error parsing JSON message", e);
        }
    }

}
