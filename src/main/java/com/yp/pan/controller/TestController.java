package com.yp.pan.controller;

import com.yp.pan.config.K8sClient;
import com.yp.pan.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@RestController
public class TestController {
    @Autowired
    ClusterService clusterService;

    @RequestMapping("/test")
    public Object test(HttpServletRequest request) {
//        NodeMe
        return new K8sClient(clusterService).get().namespaces().list(2, "eyJ2IjoibWV0YS5rOHMuaW8vdjEiLCJydiI6MjA0MTgwOSwic3RhcnQiOiJrdWJlLXB1YmxpY1x1MDAwMCJ9");
    }
}
