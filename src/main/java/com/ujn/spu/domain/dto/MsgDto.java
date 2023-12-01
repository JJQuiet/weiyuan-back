package com.ujn.spu.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class MsgDto {
    private List<String> phoneArr;
    private String msgContent;
}
