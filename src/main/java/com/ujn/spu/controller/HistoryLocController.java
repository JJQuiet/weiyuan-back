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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import com.ujn.spu.service.HistoryLocService;
import com.ujn.spu.domain.vo.HistoryLocVO;
import com.ujn.spu.domain.dto.PageDTO;
import com.ujn.spu.domain.dto.HistoryLocDTO;
import com.ujn.spu.domain.vo.base.PageVO;

/**
 * @author Richard.Lee
 */
@Slf4j
@Tag(name = "Historyloc_tbl", description = "该接口为数据接口，主要用来数据的增删改查的基本操作。")
@Validated
@RestController
@RequestMapping("/historyloc")
public class HistoryLocController {

	@Autowired
	private HistoryLocService hlService;

	@Operation(summary = "获取列表", description = "前端分页数据，返回列表数据", tags = {"historyloc"})
	@PostMapping(value = "/getArticles")	
	public ResponseEntity<PageVO<HistoryLocVO>> getArticles(@NotNull PageDTO page) {
		PageVO<HistoryLocVO> hlListPage = hlService.listTheData(page);
		return ResponseEntity.ok(hlListPage);
	}

	@Operation(summary = "创建表项", description = "创建表项", tags = {"historyloc"})
	@PostMapping(value = "/createArticle")	
	public ResponseEntity<Void> createArticle(HistoryLocDTO hlDTO) {
		hlService.createArticle(hlDTO);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "删除表项", description = "删除表项", tags = {"historyloc"})
	@PostMapping(value = "/deleteArticle")
	@Parameters({
			@Parameter(name = "id", description = "表项id", required = true),
	})
	public ResponseEntity<Void> deleteById(String id) {
		hlService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	//updateArticle
	@Operation(summary = "更新表项", description = "更新表项", tags = {"historyloc"})
	@PostMapping(value = "/updateArticle")
	@Parameters({
			@Parameter(name = "id", description = "表项id", required = true),
	})
	public ResponseEntity<Void> updateById(String id,HistoryLocDTO hlDTO) {
		hlService.updateById(id,hlDTO);
		return ResponseEntity.ok().build();
	}

}
