package com.yp.pan.aspect;

import com.yp.pan.annotation.RequireRole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleAop {

//    @Pointcut("@annotation(com.yp.pan.annotation.RequireRole)")
//    private void cut() {
//    }

    @Around("@annotation(requireRole)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, RequireRole requireRole) {
        String value = requireRole.value();
        System.out.println(value);
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
