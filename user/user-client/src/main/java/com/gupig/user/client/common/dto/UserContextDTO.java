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
    @NotBlank(message = "tenantCode cannot be blank")
    private String tenantCode;

    /**
     * 业务线
     */
    @NotBlank(message = "bizCode cannot be blank")
    private String bizCode;

    /**
     * 用户账号编码
     */
    @NotBlank(message = "uaCode cannot be blank")
    private String uaCode;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户名
     */
    private String nickname;

}
