package com.yp.pan.controller;

import com.yp.pan.config.K8sClient;
import com.yp.pan.util.UUIDUtil;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
public class TestController {

    @Autowired
    KubernetesClient client;

    @RequestMapping("/test")
    public Object test() {
        List<Namespace> items = client.namespaces().list().getItems();
        return items;
    }
}
