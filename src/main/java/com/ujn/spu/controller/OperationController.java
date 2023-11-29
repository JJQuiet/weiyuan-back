package com.ujn.spu.controller;

import com.ujn.spu.core.AjaxResult;
import com.ujn.spu.core.WsClientOper;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/opt")
public class OperationController {

    private WsClientOper wsClientOper;
    /**
     * 打开设备
     *
     */
    @GetMapping("/ws/open")
    public AjaxResult openWs() throws IOException {
        wsClientOper= new WsClientOper();
        return wsClientOper.wsConnect();
    }
    /**
     *  关闭设备
     */
    @GetMapping("/ws/close")
    public AjaxResult closeWs() throws IOException {
        if (wsClientOper!=null)
            return wsClientOper.wsDisCnct();
        else return AjaxResult.success("ws已经关闭");
    }
}
