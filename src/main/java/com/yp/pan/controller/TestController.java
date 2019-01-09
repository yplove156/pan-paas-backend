package com.yp.pan.controller;

import com.yp.pan.config.K8sClient;
import com.yp.pan.service.ClusterService;
import io.fabric8.kubernetes.api.model.extensions.Ingress;
import io.fabric8.kubernetes.api.model.extensions.IngressSpec;
import io.fabric8.kubernetes.client.KubernetesClient;
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
        KubernetesClient kubernetesClient = K8sClient.init(clusterService);
        Ingress ingress = new Ingress();
        IngressSpec ingressSpec = new IngressSpec();
        kubernetesClient.extensions().ingresses().inNamespace("").createOrReplace();
        return null;
    }
}
