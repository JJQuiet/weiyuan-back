package com.ujn.spu.service;

import com.ujn.spu.domain.dto.RtLocDTO;
import com.ujn.spu.domain.dto.PageDTO;
import com.ujn.spu.domain.entity.RtLocEntity;
import com.ujn.spu.domain.vo.RtLocVO;
import com.ujn.spu.domain.vo.base.PageVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: Richard.Lee
 * @Date: created by 2021/3/25
 */
public interface RtLocService {

	void createArticle(RtLocDTO rlDTO);	
	void deleteById(String id);
	void updateById(String id,RtLocDTO rlDTO);
	/**
	 * 分页查询
	 * 列出表格数据
	 * @param page 分页数据，前端起始页1，默认每页条数10
	 * @return
	 */
	PageVO<RtLocVO> listTheData(PageDTO page);
	List<RtLocEntity> listALLData();
}
