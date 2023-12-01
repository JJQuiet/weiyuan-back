package com.ujn.spu.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ujn.spu.domain.entity.HistoryLocEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;


/**
 * @Author: Richard.Lee
 * @Date: created by 2021/4/1
 */
@Data
@Schema(name = "历史位置信息描述", description = "")
public class HistoryLocVO {

	@Schema(description = "数据id")
	private Long id;

	@Schema(description = "日期时间")
	private String datetime;

	@Schema(description = "经度")
	private String longitude;

	@Schema(description = "维度")
	private String latitude;

	@Schema(description = "用户id")
	private String userid;

	@Schema(description = "速度")
	private String velocity;

	@Schema(description = "海拔")
	private String height;
	
	@Schema(description = "参数")
	private String para;

	public static HistoryLocVO create(HistoryLocEntity hlEntity){
		HistoryLocVO hlVO = new HistoryLocVO();
		BeanUtils.copyProperties(hlEntity, hlVO, HistoryLocVO.class);
		return hlVO;
	}
}
