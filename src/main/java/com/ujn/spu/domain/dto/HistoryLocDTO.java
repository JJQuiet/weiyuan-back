package com.ujn.spu.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
//import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Richard.Lee
 * @Date: created by 2021/2/20
 */
@Data
@Schema(name = "创建表项 DTO", description = "创建表项时需要提交的表单数据", required = true)
public class HistoryLocDTO {
	@Schema(description = "表项1", required = true)
	private String datetime;
	
	@Schema(description = "表项2", required = true)
	private String longitude;

	@Schema(description = "表项3", required = true)
	private String latitude;

	@Schema(description = "表项4", required = false)
	private String userid;

	@Schema(description = "表项5", required = false)
	private String velocity;
	
	@Schema(description = "表项6", required = false)
	private String height;

	@Schema(description = "表项7", required = false)
	private String para;
}
