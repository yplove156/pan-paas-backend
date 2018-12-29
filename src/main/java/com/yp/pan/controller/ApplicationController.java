package com.yp.pan.controller;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.DeleteAppDto;
import com.yp.pan.dto.DeployDto;
import com.yp.pan.dto.StartAppDto;
import com.yp.pan.dto.StopAppDto;
import com.yp.pan.service.ApplicationService;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/stop")
    public Object stopApp(@RequestBody StopAppDto appDto,
                          @RequestAttribute String userId,
                          @RequestAttribute String role) {
        if (StringUtils.isEmpty(appDto.getName()) || StringUtils.isEmpty(appDto.getNamespace())) {
            throw new ServerException(CustomEnum.STOP_APPLICATION_ERROR);
        }
        return applicationService.stopApp(appDto, userId, role);
    }

    @PutMapping("/start")
    public Object startApp(@RequestBody StartAppDto appDto,
                          @RequestAttribute String userId,
                          @RequestAttribute String role) {
        if (StringUtils.isEmpty(appDto.getName()) || StringUtils.isEmpty(appDto.getNamespace())) {
            throw new ServerException(CustomEnum.START_APPLICATION_ERROR);
        }
        return applicationService.startApp(appDto, userId, role);
    }

    @PutMapping("/delete")
    public Object deleteApp(@RequestBody DeleteAppDto appDto,
                            @RequestAttribute String userId,
                            @RequestAttribute String role){
        if (StringUtils.isEmpty(appDto.getName()) || StringUtils.isEmpty(appDto.getNamespace())) {
            throw new ServerException(CustomEnum.START_APPLICATION_ERROR);
        }
        return applicationService.deleteApp(appDto, userId, role);
    }
}
