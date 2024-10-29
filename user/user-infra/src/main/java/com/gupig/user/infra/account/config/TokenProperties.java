package com.gupig.user.infra.account.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * token的配置属性
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenProperties {

    /**
     * token的key
     */
    private String key = "foanjgnapeiojgeignjeui";

}