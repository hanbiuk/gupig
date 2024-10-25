package com.gupig.user.client.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 账号登陆 命令参数
 *
 * @author hanbiuk
 * @date 2024-10-14
 */
@Data
public class AccountLogInCmd implements Serializable {

    /**
     * 租户编码
     */
    @NotBlank(message = "tenantCode cannot be blank")
    private String tenantCode;

    /**
     * 业务线
     */
    @NotBlank(message = "bizCode cannot be blank")
    private String bizCode;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verificationCode;

    /**
     * 图形验证码
     */
    @NotBlank(message = "captcha cannot be blank")
    private String captcha;

}
