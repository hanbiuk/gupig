package com.gupig.user.client.notice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 图形验证码获取 命令参数
 *
 * @author hanbiuk
 * @date 2024-11-05
 */
@Data
public class NoticeCaptchaGetCmd implements Serializable {

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
     * 场景(eg.注册|登陆)
     */
    @NotBlank(message = "functionCode cannot be blank")
    private String functionCode;

    /**
     * 用户身份(已登陆为uaCode, 未登陆为邮箱或手机号)
     */
    @NotBlank(message = "userIdentity cannot be blank")
    private String userIdentity;

}
