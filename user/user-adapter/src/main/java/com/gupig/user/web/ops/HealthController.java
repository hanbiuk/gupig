package com.gupig.user.web.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 健康检查接口
 *
 * @author hanbiuk
 * @date 2024-10-09
 */
@RestController
@RequestMapping("user/ops/health")
public class HealthController {

    @GetMapping("")
    public String runtime() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String localHost = "";
        try {
            localHost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException ignored) {
        }
        return String.format("UserApplication on %s, has ran %s s", localHost, runtime.getUptime() / 1000);
    }

}