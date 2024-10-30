package com.gupig.user.client.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户上下文DTO
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Data
public class UserContextDTO {

    /**
     * 登陆凭证
     */
    @NotBlank(message = "token cannot be blank")
    private String token;

    /**
     * 登陆凭证过期时间
     */
    private LocalDateTime cstExpire;

    /**
     * 租户编码
     */
    @NotBlank(message = "optTenantCode cannot be blank")
    private String optTenantCode;

    /**
     * 业务线
     */
    @NotBlank(message = "optBizCode cannot be blank")
    private String optBizCode;

    /**
     * 用户账号编码
     */
    @NotBlank(message = "optUaCode cannot be blank")
    private String optUaCode;

    /**
     * 用户名
     */
    private String optUsername;

    /**
     * 用户名
     */
    private String optNickname;

}
