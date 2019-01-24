package com.yp.pan.controller;

import com.yp.pan.config.K8sClient;
import com.yp.pan.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
public class TestController {
    @Autowired
    ClusterService clusterService;

    @RequestMapping("/test")
    public Object test() {
        int size = K8sClient.init(clusterService).pods().inAnyNamespace().list().getItems().size();
        return null;
    }
}
