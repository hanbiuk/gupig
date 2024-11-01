package com.gupig.user.infra.account.dataobject;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户账号表 数据对象
 *
 * @author hanbiuk
 * @date 2024-10-28
 */
@Data
public class AccountDO {

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
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码加盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别: 0-未知, 1-男, 2-女
     */
    private Integer gender;

    /**
     * 出生年月日
     */
    private LocalDate birthday;

    /**
     * 头像
     */
    private String portrait;

    /**
     * 状态: 1-启用, 2-禁用, 3-销号
     */
    private Integer status;

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
