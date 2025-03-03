package io.shulie.takin.web.data.model.mysql;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author fanxx
 * @date 2020/12/31 下午2:22
 */
@Data
@TableName(value = "t_leakcheck_config")
public class LeakcheckConfigEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 引用id
     */
    @TableField(value = "business_activity_id")
    private Long businessActivityId;

    /**
     * 数据源id
     */
    @TableField(value = "datasource_id")
    private Long datasourceId;

    /**
     * 漏数sql主键集合，逗号分隔
     */
    @TableField(value = "leakcheck_sql_ids")
    private String leakcheckSqlIds;

    /**
     * 租户id
     */
    @TableField(value = "customer_id", fill = FieldFill.INSERT)
    private Long customerId;

    /**
     * 用户id
     */
    @TableField(value = "user_id", fill = FieldFill.INSERT)
    private Long userId;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否有效 0:有效;1:无效
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    public static final String COL_ID = "id";

    public static final String COL_REF_ID = "ref_id";

    public static final String COL_LEAKCHECK_SQL_IDS = "leakcheck_sql_ids";

    public static final String COL_CUSTOMER_ID = "customer_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_REMARK = "remark";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_IS_DELETED = "is_deleted";
}
