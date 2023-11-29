package com.ujn.spu.dao;

import com.ujn.spu.domain.entity.HistoryLocEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Richard.Lee
 * @Date: created by 2021/3/8
 */
public interface HistoryLocRepository extends JpaRepository<HistoryLocEntity, Long> {
	
	Page<HistoryLocEntity> findAll(Pageable pageable);
	Optional<HistoryLocEntity> findById(Long id);
	Optional<HistoryLocEntity> findFirstByOrderByIdDesc();
	Optional<HistoryLocEntity> findFirstByOrderByIdAsc();
	List<HistoryLocEntity> findFirst100ByOrderByIdAsc();
	@Override
	void deleteAllInBatch(Iterable<HistoryLocEntity> entities);
}
