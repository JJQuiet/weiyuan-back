package com.ujn.spu.service.impl;

import com.ujn.spu.dao.HistoryLocRepository;
import com.ujn.spu.domain.dto.PageDTO;
import com.ujn.spu.domain.dto.HistoryLocDTO;
import com.ujn.spu.domain.entity.HistoryLocEntity;
import com.ujn.spu.domain.entity.RtLocEntity;
import com.ujn.spu.domain.vo.HistoryLocVO;
import com.ujn.spu.domain.vo.base.PageVO;
import com.ujn.spu.exception.BizException;
import com.ujn.spu.exception.msg.BizMessage;
import com.ujn.spu.service.HistoryLocService;

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
public class HistoryLocServiceImpl implements HistoryLocService {

	@Autowired
	private HistoryLocRepository hlRepository;

	@Override
	public void createArticle(HistoryLocDTO hlDTO) {

		HistoryLocEntity hlEntity=new HistoryLocEntity();
		
		BeanUtils.copyProperties(hlDTO, hlEntity);
		//hlEntity.setType(hlDTO.getAuthor().substring(0,2));
		hlEntity = hlRepository.save(hlEntity);

		//log.info("添加列表成功: 表项1{}, 表项2{}, 表项3{}", hlDTO.getAuthor(),hlDTO.getStatus(),hlEntity.getType());
	}

	/**
	 * 删除表项
	 *
	 * @param id
	 */
	@Override
	public void deleteById(String id) {
		Long idl=Long.valueOf(id);
		HistoryLocEntity hlEntity = hlRepository.findById(idl).orElseThrow(() -> new BizException(BizMessage.REQUEST_PARAMS_INVALIDATE));
		hlRepository.delete(hlEntity);
	}

	/**
	 * 更新表项
	 *
	 * @param id
	 */
	@Override
	public void updateById(String id,HistoryLocDTO hlDTO) {
		//log.info("更新列表: id{} 表项1{}, 表项2{}", id,hlDTO.getAuthor(),hlDTO.getStatus());
		
		Long idl=Long.valueOf(id);
		HistoryLocEntity hlEntity = hlRepository.findById(idl).orElseThrow(() -> new BizException(BizMessage.REQUEST_PARAMS_INVALIDATE));
		BeanUtils.copyProperties(hlDTO, hlEntity);
		//hlEntity.setType(hlDTO.getAuthor().substring(0,2));
		hlEntity = hlRepository.save(hlEntity);
	}

	/**
	 * 分页查询 列出	 
	 * @param page
	 * @return
	 */
	@Override
	public PageVO<HistoryLocVO> listTheData(PageDTO page) {
		//HistoryLocEntity hlEntity=new HistoryLocEntity();
		
		Page<HistoryLocEntity> hlPage = hlRepository.findAll(page.buildPageRequest());
		return pageToVO(hlPage);
	}

	@Override
	public Optional<HistoryLocEntity> findNewrestItem(){
		Optional<HistoryLocEntity> hlEn=hlRepository.findFirstByOrderByIdDesc();
		return hlEn;
	}

	@Override
	public Optional<HistoryLocEntity> findOldestItem(){
		Optional<HistoryLocEntity> hlEn=hlRepository.findFirstByOrderByIdAsc();
		return hlEn;
	}


	@Override
	public List<HistoryLocEntity> findOlder100Items(){
		List <HistoryLocEntity> enList=hlRepository.findFirst100ByOrderByIdAsc();
		return enList;
	}

	@Override
	public	void delteBatchItems(List<HistoryLocEntity> enlist) {
		hlRepository.deleteAllInBatch(enlist);
	}
	/**
	 * 数据库查询出来的各tbl的Entity Page对象转成PageVO对象
	 *
	 * @param
	 * @return
	 */
	private PageVO<HistoryLocVO> pageToVO(Page<HistoryLocEntity> hlPage) {
		List<HistoryLocVO> listVOList = hlPage.get().map(uf -> HistoryLocVO.create(uf)).collect(Collectors.toList());
		return new PageVO<>(hlPage.getTotalElements(), listVOList);
	}
}
