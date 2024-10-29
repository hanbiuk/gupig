package com.gupig.user.client.account.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 账号状态: 0-待激活, 1-启用, 2-禁用, 3-销号
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Getter
@AllArgsConstructor
public enum AccountStatusEnum {

    /**
     * 账号状态: 0-待激活, 1-启用, 2-禁用, 3-销号
     */
    INACTIVE(0, "待激活"),
    ENABLE(1, "启用"),
    DISABLE(2, "禁用"),
    SIGN_DOWN(3, "销号"),
    ;

    private final Integer code;
    private final String desc;

    public static AccountStatusEnum of(Integer code) {
        return Stream.of(values())
                .filter(item -> Objects.equals(item.code, code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists in AccountStatusEnum"));
    }

}