package com.pamirs.takin.entity.domain.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "AppUnstallAgentVo", description = "一键卸载探针入参")
public class AppUnstallAgentVo {

    private List<String> appIds;
}
