package com.gupig.user.infra.account.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户账号和业务线 数据结果
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountWithBizDataResult extends AccountDO {

    /**
     * 主键编号
     */
    private Long uabId;

    /**
     * 主键编码
     */
    private String uabCode;

    /**
     * 业务线
     */
    private String bizCode;

    /**
     * 昵称
     */
    private String uabNickname;

    /**
     * 头像
     */
    private String uabPortrait;

    /**
     * 自我简介/签名
     */
    private String uabUserDetail;

    /**
     * 状态: 1-启用, 2-禁用, 3-销号
     */
    private Integer uabStatus;

    /**
     * 激活时间
     */
    private LocalDateTime uabCstActivate;

    /**
     * 是否删除: 0-否, 1-是
     */
    private Integer uabIsDeleted;

    /**
     * 创建人
     */
    private String uabCreator;

    /**
     * 创建时间
     */
    private LocalDateTime uabCstCreate;

    /**
     * 修改人
     */
    private String uabModifier;

    /**
     * 修改时间
     */
    private LocalDateTime uabCstModify;

}
