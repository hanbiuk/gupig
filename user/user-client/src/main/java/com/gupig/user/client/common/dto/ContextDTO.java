package com.gupig.user.client.common.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 上下文DTO
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Data
public class ContextDTO {

    /**
     * 用户上下文DTO
     */
    @Valid
    @NotNull(message = "userContext cannot be null")
    private UserContextDTO userContext;

}
