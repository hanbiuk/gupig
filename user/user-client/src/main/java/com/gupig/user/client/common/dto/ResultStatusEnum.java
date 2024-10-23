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


    /**
     * 系统级别错误
     */
    SYSTEM_ERROR("10101", "系统错误"),
    PARAM_EMPTY("10102", "参数为空"),
    PARAM_ERROR("10103", "参数错误"),
    RPC_ERROR("10104", "调用业务方接口异常"),

    /**
     * 数据级别错误
     */
    DATA_NOT_EXIST("10201", "数据不存在"),
    SAVE_EXCEPTION("10202", "保存数据异常"),
    UPDATE_EXCEPTION("10203", "更新数据异常"),
    DELETE_EXCEPTION("10204", "删除数据异常"),
    SELECT_EXCEPTION("10205", "查询数据异常"),
    DATA_NO_AUTHORITY("10206", "数据权限不足"),
    DATA_STATUS_ERROR("10207", "数据状态不正确"),
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
