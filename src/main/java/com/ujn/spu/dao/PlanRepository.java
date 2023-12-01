package com.ujn.spu.dao;

import com.ujn.spu.domain.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<PlanEntity,Long> {
}
