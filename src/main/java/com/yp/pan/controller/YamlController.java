package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.annotation.RequireRole;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.LogCode;
import com.yp.pan.service.YamlService;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/yamls")
public class YamlController {

    private final YamlService yamlService;

    @Autowired
    public YamlController(YamlService yamlService) {
        this.yamlService = yamlService;
    }

    @PostMapping
    @RequireRole("admin")
    @PanLog(LogCode.DEPLOY_YAML_LOG)
    public Object deployByYaml(MultipartFile file) {
        try {
            return yamlService.deployByYaml(file.getInputStream());
        } catch (Exception e) {
            throw new ServerException(CustomEnum.YAML_DEPLOY_ERROR);
        }
    }

    @DeleteMapping
    @RequireRole("admin")
    @PanLog(LogCode.DEPLOY_YAML_LOG)
    public Object deleteByYaml(MultipartFile file) {
        try {
            return yamlService.deleteByYaml(file.getInputStream());
        } catch (Exception e) {
            throw new ServerException(CustomEnum.YAML_DEPLOY_ERROR);
        }
    }
}
