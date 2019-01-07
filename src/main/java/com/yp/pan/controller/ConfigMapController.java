package com.yp.pan.controller;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.service.ClusterService;
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

    @Autowired
    private ClusterService clusterService;

    @GetMapping
    public Object configMapList() {
        KubernetesClient client = new K8sClient(clusterService).get();
        return client.configMaps().list();
    }

    @PostMapping
    public Object createConfigMap(
            @RequestBody Map<String, String> data,
            @RequestAttribute String userId) {
        KubernetesClient client = new K8sClient(clusterService).get();
        ConfigMap configMap = new ConfigMap();
        ObjectMeta meta = new ObjectMeta();
        Map<String, String> annotations = new HashMap<>();
        annotations.put(CustomAnno.PAN_USER, userId);
        meta.setAnnotations(annotations);
        configMap.setMetadata(meta);
        configMap.setData(data);
        return client.configMaps().inNamespace("").createOrReplace(configMap);
    }
}
