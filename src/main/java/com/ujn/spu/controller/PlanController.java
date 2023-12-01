package com.ujn.spu.controller;

import com.ujn.spu.dao.PlanRepository;
import com.ujn.spu.domain.dto.MsgDto;
import com.ujn.spu.domain.entity.PlanEntity;
import com.ujn.spu.utils.MsgUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("")
@RestController
@CrossOrigin
@Slf4j
public class PlanController {
    @Autowired
    PlanRepository planRepository;

    @GetMapping("plan/add")
    public Object pingAdd(@RequestParam String addrId,
                          @RequestParam String sourceValue,
                          @RequestParam String nameValue,
                          @RequestParam String contentValue
    ){
        PlanEntity entity = new PlanEntity(null,addrId,sourceValue,nameValue,contentValue);
        entity = planRepository.save(entity);
        return entity;
    }
    @GetMapping("plan/del")
    public Object pingDel(@RequestParam Long id
    ){
        planRepository.deleteById(id);
        return "success";
    }
    @GetMapping("plan/edit")
    public Object pingEdit(@RequestParam Long id,
                           @RequestParam String addrId,
                           @RequestParam String sourceValue,
                           @RequestParam String nameValue,
                           @RequestParam String contentValue
    ){
        PlanEntity entity = new PlanEntity(id,addrId,sourceValue,nameValue,contentValue);
        entity = planRepository.save(entity);
        return entity;
    }
    @GetMapping("plan/list")
    public Object pingList(){
        return planRepository.findAll();
    }
    @PostMapping("msg/send")
    public Object msgSend(@RequestBody MsgDto msgDto){

        return MsgUnit.send(msgDto.getPhoneArr(),msgDto.getMsgContent());
    }
}
