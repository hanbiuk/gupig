package com.gupig.user.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 性别: 0-未知, 1-男, 2-女
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    /**
     * 性别: 0-未知, 1-男, 2-女
     */
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女"),
    ;

    private final Integer code;
    private final String desc;

    public static GenderEnum of(Integer code) {
        return Stream.of(values())
                .filter(item -> Objects.equals(item.code, code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists in GenderEnum"));
    }

}
