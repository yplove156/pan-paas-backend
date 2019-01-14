package com.yp.pan.aspect;

import com.sun.jndi.ldap.pool.PooledConnectionFactory;
import com.yp.pan.annotation.RequireRole;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.model.UserInfo;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
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
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        if (!userInfo.getRole().equals(value)) {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        return proceedingJoinPoint.proceed();
    }
}
