package com.ujn.spu.dao;

import com.ujn.spu.domain.entity.RtLocEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Richard.Lee
 * @Date: created by 2021/3/8
 */
public interface RtLocRepository extends JpaRepository<RtLocEntity, Long> {
	
	Page<RtLocEntity> findAll(Pageable pageable);
	List<RtLocEntity> findAll();
	Optional<RtLocEntity> findById(Long id);
}
