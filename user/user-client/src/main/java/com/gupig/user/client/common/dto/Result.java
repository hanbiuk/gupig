package com.gupig.user.client.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 统一返回结果
 *
 * @param <T> 泛型
 * @author hanbiuk
 * @date 2024-10-14
 */
@Data
public class Result<T> implements Serializable {
    /**
     * 状态码
     */
    private String code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 耗时
     */
    private Long cost;

    public void setResult(ResultStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getCode();
        this.msg = resultStatusEnum.getMsg();
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setResult(ResultStatusEnum.SUCCESS);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setResult(ResultStatusEnum.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultStatusEnum.SUCCESS.getCode());
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(ResultStatusEnum resultStatusEnum) {
        Result<T> result = new Result<>();
        result.setResult(resultStatusEnum);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> fail(ResultStatusEnum resultStatusEnum, Long cost) {
        Result<T> result = new Result<>();
        result.setResult(resultStatusEnum);
        result.setData(null);
        result.setCost(cost);
        return result;
    }

    public static <T> Result<T> fail(ResultStatusEnum resultStatusEnum, String msg) {
        Result<T> result = new Result<>();
        result.setCode(resultStatusEnum.getCode());
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> fail(ResultStatusEnum resultStatusEnum, String msg, Long cost) {
        Result<T> result = new Result<>();
        result.setCode(resultStatusEnum.getCode());
        result.setMsg(msg);
        result.setData(null);
        result.setCost(cost);
        return result;
    }

    public static <T> Result<T> fail(String code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> fail(String code, String msg, Long cost) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        result.setCost(cost);
        return result;
    }

    public static <T> Boolean isFail(Result<T> result) {
        return Objects.isNull(result) || !Objects.equals(ResultStatusEnum.SUCCESS.getCode(), result.getCode());
    }

    public static <T> Boolean isSuccess(Result<T> result) {
        return Objects.nonNull(result) && Objects.equals(ResultStatusEnum.SUCCESS.getCode(), result.getCode());
    }

}
