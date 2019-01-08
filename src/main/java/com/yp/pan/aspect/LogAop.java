package com.yp.pan.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static java.lang.System.out;

@Aspect
@Component
public class LogAop {

    private static boolean log = true;

    @Pointcut("execution(public * com.yp.pan.controller..*.*(..))")
    private void logs() {
    }

    @Before("logs()")
    public void before(JoinPoint point) {
        if (log) {
            out.print(LogAop.class.getSimpleName() + "-");
            out.print(point.getTarget().getClass().getSimpleName() + ".");
            out.print(point.getSignature().getName() + "-begin-args-");
            Object[] args = point.getArgs();
            if (args != null && args.length > 0) {
                out.print("([");
                for (Object object : point.getArgs()) {
                    try {
                        out.print(JSONObject.toJSONString(object) + ",");
                    } catch (Throwable e) {
                        if (object instanceof HttpServletRequest) {
                            HttpServletRequest request = (HttpServletRequest) object;
                            out.print("{" + request.getQueryString() + "," + JSONObject.toJSONString(request.getCookies()) + "},");
                        } else {
                            out.print(object + ",");
                        }
                    }
                }
                out.print("\b])");
            } else {
                out.print("()");
            }
            out.println();
        }
    }

    @After("logs()")
    public void after(JoinPoint point) {
        if (log) {
            out.print(LogAop.class.getSimpleName() + "-");
            out.print(point.getTarget().getClass().getSimpleName() + ".");
            out.print(point.getSignature().getName() + "-after-");
            out.println();
        }
    }

    @AfterReturning(pointcut = "logs()", returning = "result")
    public void returned(JoinPoint point, Object result) {
        if (log) {
            out.print(LogAop.class.getSimpleName() + "-");
            out.print(point.getTarget().getClass().getSimpleName() + ".");
            out.print(point.getSignature().getName() + "-returned-result-");
            out.print((result instanceof String) ? result : JSONObject.toJSONString(result));
            out.println();
        }
    }

    @AfterThrowing(pointcut = "logs()", throwing = "ex")
    public void throwed(JoinPoint point, Throwable ex) {
        if (log) {
            out.print(LogAop.class.getSimpleName() + "-");
            out.print(point.getTarget().getClass().getSimpleName() + ".");
            out.print(point.getSignature().getName() + "-throwed-ex-");
            out.print(ex.getMessage() + "-" + ex.getClass().getSimpleName());
            out.println();
//            ex.printStackTrace(out);
        }
    }
}
