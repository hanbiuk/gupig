package com.gupig.user.infra.account.dataquery;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户账号登出 数据查询参数
 *
 * @author hanbiuk
 * @date 2024-10-31
 */
@Data
public class AccountLogoutDataQry {

    /**
     * 主键编码
     */
    private String code;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 业务线
     */
    private String bizCode;

    /**
     * 用户账号编码
     */
    private String uaCode;

    /**
     * 过期时间
     */
    private LocalDateTime cstExpire;

}
