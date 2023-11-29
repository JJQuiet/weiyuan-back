package com.ujn.spu;

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
    public void run(ApplicationArguments args){
        Optional<PhoneSetEntity> opt = phoneSetRepository.findById(1l);
        if(opt.isPresent() == false)
        {
            phoneSetRepository.save(new PhoneSetEntity(1l,"6000"));
        }
    }
}