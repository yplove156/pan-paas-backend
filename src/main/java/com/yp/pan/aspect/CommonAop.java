package com.yp.pan.aspect;

import com.alibaba.fastjson.JSONObject;
import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.model.LogInfo;
import com.yp.pan.util.ResponseEntity;
import com.yp.pan.util.ServerException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * CommonAop class
 *
 * @author Administrator
 * @date 2018/11/15
 */
@Component
@Aspect
public class CommonAop {

    @Pointcut("execution(public * com.yp.pan.controller..*.*(..))")
    private void result() {
    }

    @Around("result()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        Object res;
        LogInfo logInfo = new LogInfo();
        try {
//            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
//            PanLog panLog = signature.getClass().getAnnotation(PanLog.class);
//            logInfo.setAction(panLog.value());
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            String username = (String) requestAttributes.getAttribute("username", RequestAttributes.SCOPE_REQUEST);
            logInfo.setId(UUID.randomUUID().toString());
            logInfo.setUser(username);
            logInfo.setCreateTime(System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            res = proceedingJoinPoint.proceed();
            logInfo.setStatus(true);
            logInfo.setRes(JSONObject.toJSONString(res));
        } catch (ServerException e) {
            logInfo.setStatus(false);
            logInfo.setException(e.getClass().getSimpleName());
            logInfo.setCode(e.getCode());
            logInfo.setMessage(e.getMessage());
            return ResponseEntity.getFail(e.getCode(), e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logInfo.setStatus(false);
            logInfo.setException(throwable.getClass().getSimpleName());
            logInfo.setCode(CustomEnum.COMMON_EXCEPTION.getCode());
            logInfo.setMessage(throwable.getMessage());
            return ResponseEntity.getFail(CustomEnum.COMMON_EXCEPTION.getCode(), CustomEnum.COMMON_EXCEPTION.getMsg());
        }
        CompletableFuture.runAsync(() -> {
            System.out.println("***********************************");
            System.out.println(JSONObject.toJSON(logInfo));
            System.out.println("----------------------------------");
        });
        return ResponseEntity.getSuccess(res);
    }
}
