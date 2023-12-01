package com.ujn.spu.dao;

import com.ujn.spu.domain.entity.CameraLocEntity;
import com.ujn.spu.domain.entity.PhoneSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhoneSetRepository extends JpaRepository<PhoneSetEntity,Long> {
}

