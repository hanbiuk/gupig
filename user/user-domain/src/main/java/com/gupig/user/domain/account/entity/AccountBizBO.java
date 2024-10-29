package com.gupig.user.domain.account.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户账号业务线 业务对象
 *
 * @author hanbiuk
 * @date 2024-10-28
 */
@Data
public class AccountBizBO {

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
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String portrait;

    /**
     * 自我简介/签名
     */
    private String userDetail;

    /**
     * 状态: 1-启用, 2-禁用, 3-销号
     */
    private Integer status;

    /**
     * 激活时间
     */
    private LocalDateTime cstActivate;

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
