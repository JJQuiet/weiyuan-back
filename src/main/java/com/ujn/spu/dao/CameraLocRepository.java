package com.ujn.spu.dao;

import com.ujn.spu.domain.entity.CameraLocEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CameraLocRepository extends JpaRepository<CameraLocEntity,Long> {
    @Query(value = "select * from camera_loc_entity where " +
            "camera_name like concat('%',?1,'%')" ,nativeQuery=true)
    List<CameraLocEntity> searchAllCameraName(String camera_name);
    Optional<CameraLocEntity> findByCameraName(String cameraName);

}
