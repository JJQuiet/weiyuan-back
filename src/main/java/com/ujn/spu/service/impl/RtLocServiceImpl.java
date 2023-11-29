package com.ujn.spu.service.impl;

import com.ujn.spu.dao.RtLocRepository;
import com.ujn.spu.domain.dto.PageDTO;
import com.ujn.spu.domain.dto.RtLocDTO;
import com.ujn.spu.domain.entity.RtLocEntity;
import com.ujn.spu.domain.vo.RtLocVO;
import com.ujn.spu.domain.vo.base.PageVO;
import com.ujn.spu.exception.BizException;
import com.ujn.spu.exception.msg.BizMessage;
import com.ujn.spu.service.RtLocService;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Richard.Lee
 * @Date: created by 2021/3/25
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class RtLocServiceImpl implements RtLocService {

	@Autowired
	private RtLocRepository rlRepository;

	@Override
	public void createArticle(RtLocDTO rlDTO) {
		RtLocEntity rlEntity=new RtLocEntity();
		BeanUtils.copyProperties(rlDTO, rlEntity);
		rlRepository.save(rlEntity);

		//log.info("添加列表成功: 表项1{}, 表项2{}, 表项3{}", rlDTO.getAuthor(),rlDTO.getStatus(),rlEntity.getType());
	}

	/**
	 * 删除表项
	 *
	 * @param id
	 */
	@Override
	public void deleteById(String id) {
		Long idl=Long.valueOf(id);
		RtLocEntity rlEntity = rlRepository.findById(idl).orElseThrow(() -> new BizException(BizMessage.REQUEST_PARAMS_INVALIDATE));
		rlRepository.delete(rlEntity);		
	}

	/**
	 * 更新表项
	 *
	 * @param id
	 */
	@Override
	public void updateById(String id,RtLocDTO rlDTO) {	
		//log.info("更新列表: id{} 表项1{}, 表项2{}", id,rlDTO.getAuthor(),rlDTO.getStatus());
		
		Long idl=Long.valueOf(id);
		RtLocEntity rlEntity = rlRepository.findById(idl).orElseThrow(() -> new BizException(BizMessage.REQUEST_PARAMS_INVALIDATE));
		BeanUtils.copyProperties(rlDTO, rlEntity);
		//rlEntity.setType(rlDTO.getAuthor().substring(0,2));
		rlRepository.save(rlEntity);
	}

	/**
	 * 分页查询 列出	 
	 * @param page
	 * @return
	 */
	@Override
	public PageVO<RtLocVO> listTheData(PageDTO page) {
		RtLocEntity rlEntity=new RtLocEntity();

		Page<RtLocEntity> rlPage = rlRepository.findAll(page.buildPageRequest());
		return pageToVO(rlPage);
	}

	@Override
	public List<RtLocEntity> listALLData() {
		List<RtLocEntity> rtList=rlRepository.findAll();
		return rtList;
	}

	/**
	 * 数据库查询出来的各tbl的Entity Page对象转成PageVO对象
	 *
	 * @param
	 * @return
	 */
	private PageVO<RtLocVO> pageToVO(Page<RtLocEntity> rlPage) {
		List<RtLocVO> listVOList = rlPage.get().map(uf -> RtLocVO.create(uf)).collect(Collectors.toList());
		return new PageVO<>(rlPage.getTotalElements(), listVOList);
	}
}
