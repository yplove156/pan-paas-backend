package com.yp.pan.aspect;

import com.alibaba.fastjson.JSONObject;
import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.model.LogInfo;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.LogService;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author Administrator
 */
@Aspect
@Component
public class PanLogAop {

    private final LogService logService;

    @Autowired
    public PanLogAop(LogService logService) {
        this.logService = logService;
    }

    @Around("@annotation(panLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, PanLog panLog) throws Throwable {
        Object res;
        LogInfo logInfo = new LogInfo();
        try {
            logInfo.setAction(panLog.value());
            UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
            logInfo.setId(UUID.randomUUID().toString());
            logInfo.setUser(userInfo.getUsername());
            logInfo.setCreateTime(System.currentTimeMillis());
        } catch (Exception ignored) {
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
            throw e;
        } catch (Throwable throwable) {
            logInfo.setStatus(0);
            logInfo.setException(throwable.getClass().getSimpleName());
            logInfo.setCode(CustomEnum.COMMON_EXCEPTION.getCode());
            logInfo.setMessage(throwable.getMessage());
            CompletableFuture.runAsync(() -> logService.addLog(logInfo));
            throw throwable;
        }
        CompletableFuture.runAsync(() -> logService.addLog(logInfo));
        return res;
    }
}
