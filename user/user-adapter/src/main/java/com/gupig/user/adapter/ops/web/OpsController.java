package com.gupig.user.adapter.ops.web;

import com.gupig.user.client.common.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 运维 前端控制器
 *
 * @author hanbiuk
 * @date 2024-10-09
 */
@RestController
@RequestMapping("user/ops")
public class OpsController {

    /**
     * 健康检查
     *
     * @return 服务已运行时间
     */
    @GetMapping("health")
    public Result<String> health() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String localHost = "";
        try {
            localHost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException ignored) {
        }
        return Result.success(String.format("UserApplication on %s, has ran %s s", localHost, runtime.getUptime() / 1000));
    }

}