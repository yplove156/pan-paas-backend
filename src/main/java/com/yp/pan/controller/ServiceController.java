package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.LogCode;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.ServiceDto;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.SVCService;
import com.yp.pan.util.ServerException;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final SVCService svcService;

    @Autowired
    public ServiceController(SVCService svcService) {
        this.svcService = svcService;
    }

    @GetMapping
    public Object services() {
        return svcService.services();
    }

    @DeleteMapping("/{namespace}/{name}")
    @PanLog(LogCode.DELETE_SERVICE_LOG)
    public Object deleteService(
            @PathVariable String namespace,
            @PathVariable String name) {
        return svcService.deleteService(namespace, name);
    }

    @PostMapping
    @PanLog(LogCode.ADD_SERVICE_LOG)
    public Object addService(@RequestBody ServiceDto serviceDto) {
        return svcService.addService(serviceDto);
    }

}
