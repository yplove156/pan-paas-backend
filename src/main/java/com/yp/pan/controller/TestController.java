package com.yp.pan.controller;

import com.sun.jndi.ldap.pool.PooledConnectionFactory;
import com.yp.pan.config.K8sClient;
import com.yp.pan.util.UUIDUtil;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.Name;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public Object test(HttpServletRequest request) {
//        NodeMe
//        client.secrets().createOrReplace()
        return null;
    }
}
