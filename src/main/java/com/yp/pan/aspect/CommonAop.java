package com.yp.pan.aspect;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.util.ResponseEntity;
import com.yp.pan.util.ServerException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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
        } catch (ServerException e) {
            return ResponseEntity.getFail(e.getCode(), e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return ResponseEntity.getFail(CustomEnum.COMMON_EXCEPTION.getCode(), CustomEnum.COMMON_EXCEPTION.getMsg());
        }
        return ResponseEntity.getSuccess(res);
    }
}
