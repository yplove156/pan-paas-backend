package com.yp.pan.controller;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.service.ClusterService;
import com.yp.pan.util.ServerException;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ClusterService clusterService;

    @GetMapping
    public Object services() {
        KubernetesClient client = new K8sClient(clusterService).get();
        return client.services().list();
    }

    @DeleteMapping("/{namespace}/{name}")
    public Object deleteService(
            @PathVariable String namespace,
            @PathVariable String name,
            @RequestAttribute String role,
            @RequestAttribute String userId) {
        KubernetesClient client = new K8sClient(clusterService).get();
        Boolean delete = false;
        if (RoleEnum.ADMIN.getRole().equals(role)) {
            delete = client.configMaps().inNamespace(namespace).withName(name).delete();
        } else {
            ConfigMap configMap = client.configMaps().inNamespace(namespace).withName(name).get();
            String owner = configMap.getMetadata().getAnnotations().get(CustomAnno.PAN_USER);
            if (null != owner && owner.equals(userId)) {
                delete = client.configMaps().inNamespace(namespace).withName(name).delete();
            }
        }
        if (delete) {
            return name;
        }
        throw new ServerException(CustomEnum.DELETE_SERVICE_ERROR);
    }

}
