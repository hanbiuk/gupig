package com.gupig.user.client.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结果状态枚举
 *
 * @author hanbiuk
 * @date 2024-10-10
 */
@Getter
@AllArgsConstructor
public enum ResultStatusEnum {

    /**
     * 基础部分
     */
    SUCCESS("200", "成功"),
    UNAUTHORIZED("401", "未授权"),
    ERROR("500", "服务器异常"),
    ;

    /**
     * 状态码
     */
    private final String code;

    /**
     * 信息
     */
    private final String msg;

}
