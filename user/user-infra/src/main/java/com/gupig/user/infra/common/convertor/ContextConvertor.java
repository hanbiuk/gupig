package com.gupig.user.infra.common.convertor;

import cn.hutool.jwt.JWT;
import com.gupig.user.client.common.dto.UserContextDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 上下文对象 转换器
 *
 * @author hanbiuk
 * @date 2024-10-30
 */
@Component
public class ContextConvertor {

    /**
     * 构建用户上下文
     *
     * @param token 登陆凭证
     * @return 用户上下文
     */
    public UserContextDTO buildUserContext(String token) {
        JWT jwt = JWT.of(token);
        if (!jwt.verify()) {
            return null;
        }

        UserContextDTO userContextDTO = new UserContextDTO();
        userContextDTO.setToken(token);

        userContextDTO.setCstExpire(jwt.getPayloads().get("cstExpire", LocalDateTime.class));
        userContextDTO.setOptTenantCode(jwt.getPayloads().getStr("tenantCode"));
        userContextDTO.setOptBizCode(jwt.getPayloads().getStr("bizCode"));
        userContextDTO.setOptUaCode(jwt.getPayloads().getStr("uaCode"));
        userContextDTO.setOptUsername(jwt.getPayloads().getStr("username"));
        userContextDTO.setOptNickname(jwt.getPayloads().getStr("nickname"));

        return userContextDTO;
    }

}
