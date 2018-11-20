package com.yp.pan.aspect;

import com.yp.pan.annotation.RequireRole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@SuppressWarnings("AlibabaRemoveCommentedCode")
@Aspect
@Component
public class RoleAop {

    @Around("@annotation(requireRole)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, RequireRole requireRole) throws Throwable {
        String value = requireRole.value();
        System.out.println(value);
        return proceedingJoinPoint.proceed();
    }
}
