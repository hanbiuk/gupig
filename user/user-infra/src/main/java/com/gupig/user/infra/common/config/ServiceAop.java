package com.gupig.user.infra.common.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import com.alibaba.fastjson2.JSON;
import com.gupig.user.client.common.dto.ContextDTO;
import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.common.dto.ResultStatusEnum;
import com.gupig.user.client.common.dto.UserContextDTO;
import com.gupig.user.domain.account.repo.AccountLogoutRepository;
import com.gupig.user.infra.account.config.TokenProperties;
import com.gupig.user.infra.common.convertor.ContextConvertor;
import com.gupig.user.infra.common.exception.BizException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * service切面
 *
 * @author hanbiuk
 * @date 2024-10-16
 */
@Slf4j
@Aspect
@Component
public class ServiceAop {

    @Resource
    private AccountLogoutRepository accountLogoutRepository;

    @Resource
    private TokenProperties tokenProperties;

    @Resource
    private ContextConvertor contextConvertor;

    /**
     * 环绕方法
     *
     * @param pjp 切点
     * @return 返回结果
     */
    @Around("execution(* com.gupig.user.app..*ServiceImpl.*(..))")
    public Object around(ProceedingJoinPoint pjp) {
        StringBuilder classAndMethod = new StringBuilder();
        long startTime = System.currentTimeMillis();

        // 1. 获取方法、返回参数类型
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Class<?> currentClass = method.getDeclaringClass();
        classAndMethod.append(currentClass.getName()).append("#").append(method.getName());
        Class<?> returnType = signature.getReturnType();

        try {
            // 2. 打印参数
            this.logParam(pjp, classAndMethod);

            // 3. 获取并验证登陆凭证
            UserContextDTO userContext = this.verifyToken();

            // 4. 上下文信息设置进参数
            String userCode = this.setContext(pjp, userContext);

            // 5. 执行方法
            Object result = pjp.proceed();

            // 6. 设置响应时间
            long cost = System.currentTimeMillis() - startTime;
            if (Objects.equals(returnType, Result.class)) {
                ((Result<?>) result).setCost(cost);
            }

            // 7. 打印响应
            this.logResult(result, classAndMethod, cost, userCode);

            return result;
        }
        // 8. 参数校验
        catch (ConstraintViolationException e) {
            if (!Objects.equals(returnType, Result.class)) {
                log.error("ServiceAop around ConstraintViolationException", e);
                return null;
            }

            List<String> violationMessageList = new ArrayList<>();
            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
                violationMessageList.add(constraintViolation.getMessage());
            }
            return Result.fail(ResultStatusEnum.PARAM_ERROR, violationMessageList.toString(), System.currentTimeMillis() - startTime);
        }
        // 9. 业务异常处理
        catch (BizException e) {
            if (!Objects.equals(returnType, Result.class)) {
                log.error("ServiceAop around BizException", e);
                return null;
            }

            return Result.fail(e.getErrCode(), e.getMessage(), System.currentTimeMillis() - startTime);
        }
        // 10. 其他异常处理
        catch (Throwable e) {
            log.error("ServiceAop around exception", e);

            if (!Objects.equals(returnType, Result.class)) {
                return null;
            }

            return Result.fail(ResultStatusEnum.SYSTEM_ERROR, System.currentTimeMillis() - startTime);
        }
    }

    /**
     * 获取并验证请求头中的登陆凭证
     *
     * @return 登陆凭证
     */
    private UserContextDTO verifyToken() {
        // 1. 获取请求参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            return null;
        }

        // 2. 获取请求头中的登陆凭证
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(tokenProperties.getHeaderName());
        if (StrUtil.isBlank(token)) {
            return null;
        }

        try {
            // 3. 将token转化为jwt
            JWT jwt = JWT.of(token);

            // 4. 验证jwt
            if (!jwt.verify()) {
                throw new BizException(ResultStatusEnum.UNAUTHORIZED.getCode(), ResultStatusEnum.UNAUTHORIZED.getMsg());
            }

            // 5. 验证过期时间
            LocalDateTime cstExpire = jwt.getPayloads().get("cstExpire", LocalDateTime.class);
            if (cstExpire.isBefore(LocalDateTime.now())) {
                throw new BizException(ResultStatusEnum.AUTHORIZATION_EXPIRE.getCode(), ResultStatusEnum.AUTHORIZATION_EXPIRE.getMsg());
            }

            // 6. 校验登出
            UserContextDTO userContext = contextConvertor.buildUserContext(token);
            if (tokenProperties.getCheckLogout()
                    && accountLogoutRepository.hasLogout(userContext)) {
                throw new BizException(ResultStatusEnum.AUTHORIZATION_LOGOUT.getCode(), ResultStatusEnum.AUTHORIZATION_LOGOUT.getMsg());
            }

            // 7. 返回用户上下文
            return userContext;
        } catch (Exception e) {
            log.error("ServiceAop verifyToken exception", e);
            throw new BizException(ResultStatusEnum.UNAUTHORIZED.getCode(), ResultStatusEnum.UNAUTHORIZED.getMsg());
        }
    }

    /**
     * 输出参数日志
     *
     * @param pjp            连接点
     * @param classAndMethod 类#方法
     */
    private void logParam(ProceedingJoinPoint pjp, StringBuilder classAndMethod) {
        try {
            String params = JSON.toJSONString(pjp.getArgs());
            log.info("{} params: {}", classAndMethod, params);
        } catch (Exception e) {
            log.error("ServiceAop logParams exception", e);
        }
    }

    /**
     * 设置用户信息
     *
     * @param pjp         连接点
     * @param userContext 用户上下文
     * @return 用户编码
     */
    private String setContext(ProceedingJoinPoint pjp, UserContextDTO userContext) {
        if (Objects.isNull(userContext)) {
            return null;
        }

        try {
            for (Object arg : pjp.getArgs()) {
                if (arg instanceof ContextDTO) {
                    ((ContextDTO) arg).setUserContext(userContext);
                }
            }
        } catch (Exception e) {
            log.error("ServiceAop setContext exception", e);
        }

        return userContext.getOptUaCode();
    }

    /**
     * 输出响应日志
     *
     * @param result         响应
     * @param classAndMethod 类#方法
     * @param cost           响应时间
     * @param userCode       用户编码
     */
    private void logResult(Object result, StringBuilder classAndMethod, Long cost, String userCode) {
        try {
            String resultStr = JSON.toJSONString(result);
            log.info("{} result: {}, 耗时: {}ms, userCode: {}", classAndMethod, resultStr, cost, userCode);
        } catch (Exception e) {
            log.error("ServiceAop logResult exception", e);
        }
    }

}
