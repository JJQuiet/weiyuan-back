package com.ujn.spu.dao;

import com.ujn.spu.domain.entity.VideoInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoInfoRepository extends JpaRepository<VideoInfoEntity,Long> {

    List<VideoInfoEntity> findByUploaderName(String uploaderName);
}
