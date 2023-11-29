package com.ujn.spu.controller;

//import org.hibernate.validator.constraints.Length;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import com.ujn.spu.service.RtLocService;
import com.ujn.spu.domain.vo.RtLocVO;
import com.ujn.spu.domain.dto.PageDTO;
import com.ujn.spu.domain.dto.RtLocDTO;
import com.ujn.spu.domain.vo.base.PageVO;

/**
 * @author Richard.Lee
 */
@Slf4j
@Tag(name = "Rtloc_tbl", description = "该接口为数据接口，主要用来数据的增删改查的基本操作。")
@Validated
@RestController
@RequestMapping("/rtloc")
public class RtLocController {

	@Autowired
	private RtLocService rlService;

	@Operation(summary = "获取列表", description = "前端分页数据，返回列表数据", tags = {"rtloc"})
	@PostMapping(value = "/getArticles")	
	public ResponseEntity<PageVO<RtLocVO>> getArticles(@NotNull PageDTO page) {
		PageVO<RtLocVO> rlListPage = rlService.listTheData(page);
		return ResponseEntity.ok(rlListPage);
	}

	@Operation(summary = "创建表项", description = "创建表项", tags = {"rtloc"})
	@PostMapping(value = "/createArticle")	
	public ResponseEntity<Void> createArticle(RtLocDTO rlDTO) {
		rlService.createArticle(rlDTO);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "删除表项", description = "删除表项", tags = {"rtloc"})
	@PostMapping(value = "/deleteArticle")
	@Parameters({
			@Parameter(name = "id", description = "表项id", required = true),
	})
	public ResponseEntity<Void> deleteById(String id) {
		rlService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	//updateArticle
	@Operation(summary = "更新表项", description = "更新表项", tags = {"rtloc"})
	@PostMapping(value = "/updateArticle")
	@Parameters({
			@Parameter(name = "id", description = "表项id", required = true),
	})
	public ResponseEntity<Void> updateById(String id,RtLocDTO rlDTO) {
		rlService.updateById(id,rlDTO);
		return ResponseEntity.ok().build();
	}

}
