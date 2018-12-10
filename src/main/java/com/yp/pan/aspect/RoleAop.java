package com.yp.pan.aspect;

import com.sun.jndi.ldap.pool.PooledConnectionFactory;
import com.yp.pan.annotation.RequireRole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

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
