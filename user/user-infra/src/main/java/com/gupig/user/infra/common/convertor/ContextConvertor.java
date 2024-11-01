package com.gupig.user.infra.common.convertor;

import cn.hutool.jwt.JWT;
import com.gupig.user.client.common.dto.UserContextDTO;
import com.gupig.user.infra.common.config.TokenProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 上下文对象 转换器
 *
 * @author hanbiuk
 * @date 2024-10-30
 */
@Component
public class ContextConvertor {

    @Resource
    private TokenProperties tokenProperties;

    /**
     * 生成token
     *
     * @param userContext 用户上下文信息
     * @return token
     */
    public String buildToken(UserContextDTO userContext) {
        Map<String, Object> payloads = new HashMap<>(8);
        payloads.put("tenantCode", userContext.getOptTenantCode());
        payloads.put("bizCode", userContext.getOptBizCode());
        payloads.put("uaCode", userContext.getOptUaCode());
        payloads.put("username", userContext.getOptUsername());
        payloads.put("nickname", userContext.getOptNickname());
        payloads.put("cstExpire", LocalDateTime.now().plusDays(tokenProperties.getExpireDays()));
        return JWT.create()
                .addPayloads(payloads)
                .setKey(tokenProperties.getKey().getBytes())
                .sign();
    }

    /**
     * 构建用户上下文
     *
     * @param token 登陆凭证
     * @return 用户上下文
     */
    public UserContextDTO buildUserContext(String token) {
        try {
            JWT jwt = JWT.of(token);

            UserContextDTO userContextDTO = new UserContextDTO();
            userContextDTO.setToken(token);

            userContextDTO.setCstExpire(jwt.getPayloads().get("cstExpire", LocalDateTime.class));
            userContextDTO.setOptTenantCode(jwt.getPayloads().getStr("tenantCode"));
            userContextDTO.setOptBizCode(jwt.getPayloads().getStr("bizCode"));
            userContextDTO.setOptUaCode(jwt.getPayloads().getStr("uaCode"));
            userContextDTO.setOptUsername(jwt.getPayloads().getStr("username"));
            userContextDTO.setOptNickname(jwt.getPayloads().getStr("nickname"));

            return userContextDTO;
        } catch (Exception e) {
            return null;
        }
    }

}
