package com.gupig.user.app.ops.service;

import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.ops.api.OpsService;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 运维 服务实现类
 *
 * @author hanbiuk
 * @date 2024-10-15
 */
@Service
public class OpsServiceImpl implements OpsService {

    /**
     * 健康检查
     *
     * @return 服务已运行时间
     */
    @Override
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
