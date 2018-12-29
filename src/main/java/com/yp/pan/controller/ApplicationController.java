package com.yp.pan.controller;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.DeployDto;
import com.yp.pan.service.ApplicationService;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public Object deploy(@RequestBody DeployDto deployDto) {
        if (StringUtils.isEmpty(deployDto.getImageId()) || StringUtils.isEmpty(deployDto.getNamespace())) {
            throw new ServerException(CustomEnum.DEPLOY_APPLICATION_ERROR);
        }
        return applicationService.deploy(deployDto);
    }
}
