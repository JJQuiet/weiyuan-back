package com.ujn.spu.service;

import com.ujn.spu.domain.dto.HistoryLocDTO;
import com.ujn.spu.domain.dto.PageDTO;
import com.ujn.spu.domain.entity.HistoryLocEntity;
import com.ujn.spu.domain.entity.RtLocEntity;
import com.ujn.spu.domain.vo.HistoryLocVO;
import com.ujn.spu.domain.vo.base.PageVO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: Richard.Lee
 * @Date: created by 2021/3/25
 */
public interface HistoryLocService {

	void createArticle(HistoryLocDTO hlDTO);	
	void deleteById(String id);
	void updateById(String id,HistoryLocDTO hlDTO);
	/**
	 * 分页查询
	 * 列出表格数据
	 * @param page 分页数据，前端起始页1，默认每页条数10
	 * @return
	 */
	PageVO<HistoryLocVO> listTheData(PageDTO page);
	//List<HistoryLocEntity> listNewerestData();
	Optional<HistoryLocEntity> findNewrestItem();
	Optional<HistoryLocEntity> findOldestItem();
	List<HistoryLocEntity> findOlder100Items();
	void delteBatchItems(List<HistoryLocEntity> enlist);
}
