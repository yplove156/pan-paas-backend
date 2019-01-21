package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.LogCode;
import com.yp.pan.service.ConfigMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
