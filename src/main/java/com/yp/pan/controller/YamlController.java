package com.yp.pan.controller;

import com.yp.pan.annotation.RequireRole;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.service.YamlService;
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
    public Object deployYaml(MultipartFile file) throws IOException {
        return yamlService.deployYaml(file.getInputStream());
    }
}
