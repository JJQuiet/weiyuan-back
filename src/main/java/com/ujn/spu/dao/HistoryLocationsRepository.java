package com.ujn.spu.dao;

import com.ujn.spu.domain.entity.HistoryLocationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryLocationsRepository extends JpaRepository<HistoryLocationsEntity, Long> {
    // 这里可以添加自定义查询方法
}
