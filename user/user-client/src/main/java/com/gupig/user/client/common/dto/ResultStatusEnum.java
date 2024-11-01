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
    AUTHORIZATION_EXPIRE("402", "授权已过期"),
    AUTHORIZATION_LOGOUT("403", "授权已登出"),
    ERROR("500", "服务器异常"),

    /**
     * 系统级别错误
     */
    SYSTEM_ERROR("10101", "系统错误"),
    PARAM_ERROR("10102", "参数错误"),
    RPC_ERROR("10103", "调用业务方接口异常"),
    FUNCTION_IN_PROGRESS("10104", "功能开发中"),

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

    /**
     * 账号
     */
    ACCOUNT_STATUS_NOT_ENABLE("10301", "账号不是启用状态"),
    ACCOUNT_EMAIL_SIGNED_UP("10302", "邮箱已注册"),
    ACCOUNT_EMAIL_SIGNED_UP_PASSWORD_ERROR("10303", "邮箱已注册, 但密码不匹配"),
    ACCOUNT_USERNAME_EXIST("10304", "用户名已存在"),
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
