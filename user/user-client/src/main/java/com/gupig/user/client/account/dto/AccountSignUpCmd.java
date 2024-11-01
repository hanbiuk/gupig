package com.gupig.user.client.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 账号注册 命令参数
 *
 * @author hanbiuk
 * @date 2024-10-31
 */
@Data
public class AccountSignUpCmd implements Serializable {

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
    @NotBlank(message = "username cannot be blank")
    private String username;

    /**
     * 邮箱
     */
    @NotBlank(message = "email cannot be blank")
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "password cannot be blank")
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "verificationCode cannot be blank")
    private String verificationCode;

    /**
     * 图形验证码
     */
    @NotBlank(message = "captcha cannot be blank")
    private String captcha;

}
