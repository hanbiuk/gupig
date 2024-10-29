package com.gupig.user.app.ops;

import com.gupig.user.app.ops.executor.OpsHealthExe;
import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.ops.api.OpsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 运维 服务实现类
 *
 * @author hanbiuk
 * @date 2024-10-15
 */
@Service
public class OpsServiceImpl implements OpsService {

    @Resource
    private OpsHealthExe opsHealthExe;

    /**
     * 健康检查
     *
     * @return 服务已运行时间
     */
    @Override
    public Result<String> health() {
        return opsHealthExe.execute();
    }

}
