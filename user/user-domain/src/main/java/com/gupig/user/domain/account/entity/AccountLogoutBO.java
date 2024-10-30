package com.gupig.user.domain.account.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户账号登出 业务对象
 *
 * @author hanbiuk
 * @date 2024-10-30
 */
@Data
public class AccountLogoutBO {

    /**
     * 主键编号
     */
    private Long id;

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

    /**
     * 登陆凭证
     */
    private String token;

    /**
     * 过期时间
     */
    private LocalDateTime cstExpire;

    /**
     * 是否删除: 0-否, 1-是
     */
    private Integer isDeleted;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private LocalDateTime cstCreate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private LocalDateTime cstModify;

}
