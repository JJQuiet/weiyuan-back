package com.ujn.spu;

import com.ujn.spu.config.GlobalData;
import com.ujn.spu.config.WebConf;
import com.ujn.spu.dao.PhoneSetRepository;
import com.ujn.spu.domain.entity.PhoneSetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component

public class SerialPortRunner implements org.springframework.boot.ApplicationRunner {
    @Autowired
    PhoneSetRepository phoneSetRepository;

    @Autowired
    WebConf webConf;

    public void run(ApplicationArguments args){
        //
        GlobalData.webConf = webConf;

        //手柄号码设置
        Optional<PhoneSetEntity> opt = phoneSetRepository.findById(1l);
        if(opt.isPresent() == false)
        {
            phoneSetRepository.save(new PhoneSetEntity(1l,"6000"));
        }
    }
}