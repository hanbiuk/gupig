package com.gupig.user.client.ops.api;

import com.gupig.user.client.common.dto.Result;

/**
 * 运维 服务类
 *
 * @author hanbiuk
 * @date 2024-10-15
 */
public interface OpsService {

    /**
     * 健康检查
     *
     * @return 服务已运行时间
     */
    Result<String> health();

}
