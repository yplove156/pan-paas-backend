package com.yp.pan.aspect;

import com.yp.pan.annotation.PanLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author Administrator
 */
@SuppressWarnings("AlibabaRemoveCommentedCode")
@Aspect
@Component
public class PanLogAop {

    @Around("@annotation(panLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, PanLog panLog) throws Throwable {
        String value = panLog.value();
        System.out.println(value);
        CompletableFuture.runAsync(() -> {
            try {

            } catch (Exception e) {
                System.out.println("记录日志失败");
            }
        });
        return proceedingJoinPoint.proceed();
    }
}
