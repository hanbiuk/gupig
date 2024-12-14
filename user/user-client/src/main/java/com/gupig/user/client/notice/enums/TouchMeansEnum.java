package com.gupig.user.client.notice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 触达方式: 邮箱/短信
 *
 * @author hanbiuk
 * @date 2024-12-04
 */
@Getter
@AllArgsConstructor
public enum TouchMeansEnum {

    /**
     * 触达方式: 邮箱/短信
     */
    EMAIL("email", "email", "邮箱"),
    SMS("sms", "sms", "短信"),
    TEL("tel", "telephone", "电话"),
    ;

    private final String code;
    private final String desc;
    private final String descCn;

    public static TouchMeansEnum of(String code) {
        return Stream.of(values())
                .filter(item -> Objects.equals(item.code, code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists in TouchMeansEnum"));
    }

}
