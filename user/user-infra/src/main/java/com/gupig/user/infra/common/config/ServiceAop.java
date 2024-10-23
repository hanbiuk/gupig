package com.gupig.user.infra.common.config;

import com.alibaba.fastjson2.JSON;
import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.common.dto.ResultStatusEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
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

    /**
     * 环绕方法
     *
     * @param pjp 切点
     * @return 返回结果
     */
    @Around("execution(* com.gupig.*.app.*.service.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) {
        StringBuilder classAndMethod = new StringBuilder();
        Class<?> returnType;
        try {
            long startTime = System.currentTimeMillis();

            // 1. 获取方法、返回参数类型
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();
            Class<?> currentClass = method.getDeclaringClass();
            classAndMethod.append(currentClass.getName()).append("#").append(method.getName());
            returnType = signature.getReturnType();

            // 2. 打印参数
            this.logParam(pjp, classAndMethod);

            // 3. 用户信息设置
            Long userId = this.setUserInfo(pjp);

            // 4. 执行方法
            Object result = pjp.proceed();

            // 5. 设置响应时间
            long cost = System.currentTimeMillis() - startTime;
            if (Objects.equals(returnType, Result.class)) {
                ((Result<?>) result).setCost(cost);
            }

            // 6. 打印响应
            this.logResult(result, classAndMethod, cost, userId);

            return result;
        }
        // 7. 参数校验
        catch (ConstraintViolationException e) {
            List<String> violationMessageList = new ArrayList<>();
            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
                violationMessageList.add(constraintViolation.getMessage());
            }
            return Result.fail(ResultStatusEnum.PARAM_ERROR, violationMessageList.toString());
        }
        // 8. 统一异常处理
        catch (Throwable throwable) {
            log.error("ServiceLogAop getClassAndMethod exception", throwable);
            return Result.fail(ResultStatusEnum.SYSTEM_ERROR);
        } finally {
            // 9. 移除用户信息
            this.removeUserInfo();
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
            log.error("ServiceLogAop logParams exception", e);
        }
    }

    /**
     * 设置用户信息
     *
     * @param pjp 连接点
     * @return 用户编号
     */
    private Long setUserInfo(ProceedingJoinPoint pjp) {
        Long userId = null;
//        try {
//            UserInfoDTO userInfoDTO = new UserInfoDTO();
//            for (Object arg : pjp.getArgs()) {
//                if (arg instanceof AdminServiceParam) {
//                    // 1. 设置用户信息参数
//                    if (!ObjectUtils.isEmpty(((AdminServiceParam) arg).getSessionTenantId())) {
//                        Long sessionTenantId = ((AdminServiceParam) arg).getSessionTenantId();
//                        if (Objects.nonNull(sessionTenantId)) {
//                            ((AdminServiceParam) arg).setAdminTenantId(sessionTenantId);
//                        }
//                    }
//                    if (!ObjectUtils.isEmpty(((AdminServiceParam) arg).getAdminUserId())) {
//                        userId = ((AdminServiceParam) arg).getAdminUserId();
//                    }
//
//                    // 2. 设置用户信息
//                    userInfoDTO.setTenantId(((AdminServiceParam) arg).getAdminTenantId());
//                    userInfoDTO.setUserId(userId);
//                    userInfoDTO.setUserName(((AdminServiceParam) arg).getAdminUserName());
//                }
//            }
//            // 3. 设置用户信息上下文
//            if (Objects.isNull(BaseContext.getUserInfo())) {
//                BaseContext.setUserInfo(userInfoDTO);
//            }
//        } catch (Exception e) {
//            log.error("ServiceLogAop setUserInfo exception", e);
//        }
        return userId;
    }

    /**
     * 移除用户信息
     */
    private void removeUserInfo() {
//        BaseContext.removeUserInfo();
    }

    /**
     * 输出响应日志
     *
     * @param result         响应
     * @param classAndMethod 类#方法
     * @param cost           响应时间
     * @param userId         用户编号
     */
    private void logResult(Object result, StringBuilder classAndMethod, Long cost, Long userId) {
        try {
            String resultStr = JSON.toJSONString(result);
            log.info("{} result: {}, 耗时: {}ms, userId:{}", classAndMethod, resultStr, cost, userId);
        } catch (Exception e) {
            log.error("ServiceLogAop logResult exception", e);
        }
    }

}
