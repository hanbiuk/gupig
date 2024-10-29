package com.gupig.user.app.ops.executor;

import com.gupig.user.client.common.dto.Result;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 健康检查 执行器
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Component
public class OpsHealthExe {

    /**
     * 健康检查
     *
     * @return 服务已运行时间
     */
    public Result<String> execute() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String localHost = "";
        try {
            localHost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException ignored) {
        }
        return Result.success(String.format("UserApplication on %s, has ran %s s", localHost, runtime.getUptime() / 1000));
    }

}
