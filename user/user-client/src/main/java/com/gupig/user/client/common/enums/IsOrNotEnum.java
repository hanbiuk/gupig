package com.gupig.user.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 是否: 0-否, 1-是
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Getter
@AllArgsConstructor
public enum IsOrNotEnum {

    /**
     * 是否: 0-否, 1-是
     */
    NOT(0, "未知"),
    IS(1, "男"),
    ;

    private final Integer code;
    private final String desc;

    public static IsOrNotEnum of(Integer code) {
        return Stream.of(values())
                .filter(item -> Objects.equals(item.code, code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists in IsOrNotEnum"));
    }

}
