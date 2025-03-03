package io.shulie.takin.web.biz.pojo.output.application;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.shulie.takin.web.common.enums.shadow.ShadowMqConsumerType;
import io.shulie.takin.web.ext.entity.AuthQueryResponseCommonExt;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author by: hezhongqi
 * @date 2021/8/6 11:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ShadowConsumerOutput extends AuthQueryResponseCommonExt {
    private Long id;

    /**
     * AMDB梳理的没有入库，所有没有id
     */
    private String unionId;

    private ShadowMqConsumerType type;

    private String topicGroup;

    private Boolean enabled;

    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtUpdate;

    private Integer deleted;

    private String feature;
}
