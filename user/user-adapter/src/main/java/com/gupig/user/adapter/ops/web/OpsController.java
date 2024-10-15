package com.gupig.user.adapter.ops.web;

import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.ops.api.OpsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 运维 前端控制器
 *
 * @author hanbiuk
 * @date 2024-10-09
 */
@RestController
@RequestMapping("user/ops")
public class OpsController {

    @Resource
    private OpsService opsService;

    /**
     * 健康检查
     *
     * @return 服务已运行时间
     */
    @GetMapping("health")
    public Result<String> health() {
        return opsService.health();
    }

}