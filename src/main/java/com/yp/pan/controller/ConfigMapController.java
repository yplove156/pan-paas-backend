package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.LogCode;
import com.yp.pan.config.K8sClient;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.ConfigMapService;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapList;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/config-map")
public class ConfigMapController {

    private final ConfigMapService clusterService;

    @Autowired
    public ConfigMapController(ConfigMapService clusterService) {
        this.clusterService = clusterService;
    }

    @GetMapping
    public Object configMapList() {
        return clusterService.configMapList();
    }

    @PostMapping
    @PanLog(LogCode.ADD_CONFIGMAP_LOG)
    public Object createConfigMap(
            @RequestBody Map<String, String> data) {
        return clusterService.createConfigMap(data);
    }
}
