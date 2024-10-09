package com.gupig.user.start;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户服务启动类
 *
 * @author hanbiuk
 * @date 2024-09-29
 */
@Slf4j
@ComponentScan("com.gupig.user")
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        log.info("UserApplication start");
        SpringApplication.run(UserApplication.class, args);
        log.info("UserApplication started");
    }

}
