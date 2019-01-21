package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.LogCode;
import com.yp.pan.dto.ConfigMapDto;
import com.yp.pan.service.ConfigMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config-map")
public class ConfigMapController {

    private final ConfigMapService configMapService;

    @Autowired
    public ConfigMapController(ConfigMapService clusterService) {
        this.configMapService = clusterService;
    }

    @GetMapping
    public Object configMapList() {
        return configMapService.configMapList();
    }

    @PostMapping
    @PanLog(LogCode.ADD_CONFIGMAP_LOG)
    public Object createConfigMap(
            @RequestBody ConfigMapDto configMapDto) {
        return configMapService.createConfigMap(configMapDto);
    }

    @DeleteMapping("/namespaces/{namespace}/{name}")
    @PanLog(LogCode.DELETE_CONFIGMAP_LOG)
    public Object deleteConfigMap(@PathVariable String namespace,
                                  @PathVariable String name) {
        return configMapService.deleteConfigMap(namespace, name);
    }

    @PutMapping
    @PanLog(LogCode.UPDATE_CONFIGMAP_LOG)
    public Object updateConfigMap(@RequestBody ConfigMapDto configMapDto) {
        return configMapService.createConfigMap(configMapDto);
    }

}
