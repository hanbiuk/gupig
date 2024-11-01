package com.gupig.user.infra.account.dataquery;

import lombok.Data;

/**
 * 用户账号业务线表 数据查询参数
 *
 * @author hanbiuk
 * @date 2024-10-31
 */
@Data
public class AccountBizDataQry {

    /**
     * 主键编码
     */
    private String code;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 用户账号编码
     */
    private String uaCode;

    /**
     * 业务线
     */
    private String bizCode;

}
