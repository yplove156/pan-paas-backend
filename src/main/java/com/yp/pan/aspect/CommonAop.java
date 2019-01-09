package com.yp.pan.aspect;

import com.alibaba.fastjson.JSONObject;
import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.model.LogInfo;
import com.yp.pan.service.LogService;
import com.yp.pan.util.ResponseEntity;
import com.yp.pan.util.ServerException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LogService logService;

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
            logInfo.setStatus(1);
            logInfo.setRes(JSONObject.toJSONString(res));
        } catch (ServerException e) {
            logInfo.setStatus(0);
            logInfo.setException(e.getClass().getSimpleName());
            logInfo.setCode(e.getCode());
            logInfo.setMessage(e.getMessage());
            CompletableFuture.runAsync(() -> logService.addLog(logInfo));
            return ResponseEntity.getFail(e.getCode(), e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logInfo.setStatus(0);
            logInfo.setException(throwable.getClass().getSimpleName());
            logInfo.setCode(CustomEnum.COMMON_EXCEPTION.getCode());
            logInfo.setMessage(throwable.getMessage());
            CompletableFuture.runAsync(() -> logService.addLog(logInfo));
            return ResponseEntity.getFail(CustomEnum.COMMON_EXCEPTION.getCode(), CustomEnum.COMMON_EXCEPTION.getMsg());
        }
        CompletableFuture.runAsync(() -> logService.addLog(logInfo));
        return ResponseEntity.getSuccess(res);
    }
}
