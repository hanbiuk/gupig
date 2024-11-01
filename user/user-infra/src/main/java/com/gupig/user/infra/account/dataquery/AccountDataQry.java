package com.gupig.user.infra.account.dataquery;

import lombok.Data;

/**
 * 用户账号 数据查询参数
 *
 * @author hanbiuk
 * @date 2024-10-28
 */
@Data
public class AccountDataQry {

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 业务线
     */
    private String bizCode;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

}
