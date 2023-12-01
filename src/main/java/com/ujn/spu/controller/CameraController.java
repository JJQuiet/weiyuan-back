package com.ujn.spu.controller;

import com.ujn.spu.dao.CameraLocRepository;
import com.ujn.spu.dao.PhoneSetRepository;
import com.ujn.spu.domain.entity.CameraLocEntity;
import com.ujn.spu.domain.entity.PhoneSetEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("camera")
@RestController
@CrossOrigin
@Slf4j
public class CameraController {

    @Autowired
    CameraLocRepository cameraLocRepository;

    @Autowired
    PhoneSetRepository phoneSetRepository;

    @GetMapping("add")
    public Object add(@RequestParam String name,@RequestParam String addr,@RequestParam String lng,@RequestParam String lat){
        CameraLocEntity entity = cameraLocRepository.save(new CameraLocEntity(null,name,addr,lng,lat));
        return entity;
    }
    @GetMapping("del")
    public Object del(@RequestParam Long id){
       cameraLocRepository.deleteById(id);
        return "success";
    }
    @GetMapping("edit")
    public Object add(@RequestParam Long id,@RequestParam String name,@RequestParam String addr){
        Optional<CameraLocEntity> opt = cameraLocRepository.findById(id);
        if(opt.isPresent() == false)return "修改失败,没有该监控";
        CameraLocEntity entity = cameraLocRepository.save(new CameraLocEntity(id,name,addr,opt.get().getLngVal(),opt.get().getLatVal()));
        return entity;
    }
    @GetMapping("list")
    public Object list(){
        List<CameraLocEntity> entityArr = cameraLocRepository.findAll();
        return entityArr;
    }

    @GetMapping("findAllByName")
    public Object findAllByName(@RequestParam String cameraName){
        List<CameraLocEntity> entityArr = cameraLocRepository.searchAllCameraName(cameraName);
        return entityArr;
    }
    @GetMapping("phone/get")
    public Object phoneGet(){
        Optional<PhoneSetEntity> opt = phoneSetRepository.findById(1l);
        if(opt.isPresent() == false)
            return "数据不存在";
        return opt.get();
    }
    @GetMapping("phone/edit")
    public Object phoneEdit(String phone){
        Optional<PhoneSetEntity> opt = phoneSetRepository.findById(1l);
        if(opt.isPresent() == false)
            return "数据不存在";
        PhoneSetEntity entity = opt.get();
        entity.setPhone(phone);
        phoneSetRepository.save(entity);
        return entity;
    }
}
