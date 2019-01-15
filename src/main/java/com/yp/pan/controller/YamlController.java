package com.yp.pan.controller;

import com.yp.pan.annotation.RequireRole;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.service.YamlService;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/yamls")
public class YamlController {

    private final YamlService yamlService;

    @Autowired
    public YamlController(YamlService yamlService) {
        this.yamlService = yamlService;
    }

    @PostMapping("/deploy")
    @RequireRole("admin")
    public Object deployYaml(MultipartFile file) {
        try {
            return yamlService.deployYaml(file.getInputStream());
        } catch (IOException e) {
            throw new ServerException(CustomEnum.YAML_DEPLOY_ERROR);
        }
    }
}
