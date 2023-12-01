package com.ujn.spu.dao;

import com.ujn.spu.domain.entity.PhotoInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoInfoRepository extends JpaRepository<PhotoInfoEntity,Long> {

    List<PhotoInfoEntity> findByUploaderName(String uploaderName);
}
