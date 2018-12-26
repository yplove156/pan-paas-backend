package com.yp.pan.aspect;

import com.yp.pan.annotation.PanLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.CompletableFuture;

/**
 * @author Administrator
 */
@SuppressWarnings("AlibabaRemoveCommentedCode")
//@Aspect
//@Component
public class PanLogAop {

//    @Around("@annotation(panLog)")
//    public Object around(ProceedingJoinPoint proceedingJoinPoint, PanLog panLog) throws Throwable {
//        String value = panLog.value();
//        System.out.println(value);
//        CompletableFuture.runAsync(() -> {
//            try {
//                RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//                String username = (String) requestAttributes.getAttribute("username", RequestAttributes.SCOPE_REQUEST);
//                System.out.println(username);
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("记录日志失败");
//            }
//        });
//        return proceedingJoinPoint.proceed();
//    }
}
