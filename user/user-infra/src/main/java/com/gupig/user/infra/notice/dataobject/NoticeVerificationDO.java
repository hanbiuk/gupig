package com.gupig.user.infra.notice.dataobject;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户通知验证码表
 *
 * @author Jeffrey
 * @date 2024-12-14
 */
@Data
public class NoticeVerificationDO {

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
     * 业务线
     */
    private String bizCode;

    /**
     * 场景
     */
    private String functionCode;

    /**
     * 触达方式
     */
    private String touchMeans;

    /**
     * 用户身份
     */
    private String userIdentity;

    /**
     * 请求编码
     */
    private String requestCode;

    /**
     * 验证码
     */
    private String verification;

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