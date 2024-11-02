package com.gupig.user.client.account.dto;

import com.gupig.user.client.common.dto.ContextDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 账号销号 命令参数
 *
 * @author hanbiuk
 * @date 2024-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountSignDownCmd extends ContextDTO implements Serializable {

    /**
     * 验证码
     */
    @NotBlank(message = "verificationCode cannot be blank")
    private String verificationCode;

}
