package com.yp.pan.aspect;

import com.alibaba.fastjson.JSONObject;
import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.model.LogInfo;
import com.yp.pan.service.LogService;
import com.yp.pan.util.ResponseEntity;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
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


    @Pointcut("execution(public * com.yp.pan.controller..*.*(..))")
    private void result() {
    }

    @Around("result()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        Object res;
        try {
            res = proceedingJoinPoint.proceed();
            return ResponseEntity.getSuccess(res);
        } catch (ServerException e) {
            return ResponseEntity.getFail(e.getCode(), e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return ResponseEntity.getFail(CustomEnum.COMMON_EXCEPTION.getCode(), CustomEnum.COMMON_EXCEPTION.getMsg());
        } finally {
            ThreadLocalUtil.getInstance().remove();
        }
    }
}
