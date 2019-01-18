package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.LogCode;
import com.yp.pan.dto.AppReplicasDto;
import com.yp.pan.dto.DeleteAppDto;
import com.yp.pan.dto.DeployDto;
import com.yp.pan.dto.StartAppDto;
import com.yp.pan.dto.StopAppDto;
import com.yp.pan.service.ApplicationService;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("JavaDoc")
@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * 部署应用
     *
     * @param deployDto
     * @return
     */
    @PostMapping
    @PanLog(LogCode.DEPLOY_APP_LOG)
    public Object deploy(@RequestBody DeployDto deployDto) {
        if (StringUtils.isEmpty(deployDto.getImageId()) || StringUtils.isEmpty(deployDto.getNamespace())) {
            throw new ServerException(CustomEnum.DEPLOY_APPLICATION_ERROR);
        }
        return applicationService.deploy(deployDto);
    }

    /**
     * 停止应用，未删除
     *
     * @param appDto
     * @return
     */
    @PutMapping("/stop")
    @PanLog(LogCode.STOP_APP_LOG)
    public Object stopApp(@RequestBody StopAppDto appDto) {
        if (StringUtils.isEmpty(appDto.getName()) || StringUtils.isEmpty(appDto.getNamespace())) {
            throw new ServerException(CustomEnum.STOP_APPLICATION_ERROR);
        }
        return applicationService.stopApp(appDto);
    }

    /**
     * 启动已停止的应用，可自定义实例副本数
     *
     * @param appDto
     * @return
     */
    @PutMapping("/start")
    @PanLog(LogCode.START_APP_LOG)
    public Object startApp(@RequestBody StartAppDto appDto) {
        if (StringUtils.isEmpty(appDto.getName()) || StringUtils.isEmpty(appDto.getNamespace())) {
            throw new ServerException(CustomEnum.START_APPLICATION_ERROR);
        }
        return applicationService.startApp(appDto);
    }

    /**
     * 删除应用
     *
     * @param appDto
     * @return
     */
    @PutMapping("/delete")
    @PanLog(LogCode.DELETE_APP_LOG)
    public Object deleteApp(@RequestBody DeleteAppDto appDto) {
        if (StringUtils.isEmpty(appDto.getName()) || StringUtils.isEmpty(appDto.getNamespace())) {
            throw new ServerException(CustomEnum.DELETE_APPLICATION_ERROR);
        }
        return applicationService.deleteApp(appDto);
    }

    /**
     * 设置应用副本
     *
     * @param appReplicasDto
     * @return
     */
    @PutMapping("/replicas")
    @PanLog(LogCode.UPDATE_APP_REPLICAS_LOG)
    public Object updateReplicas(@RequestBody AppReplicasDto appReplicasDto) {
        if (StringUtils.isEmpty(appReplicasDto.getName()) || StringUtils.isEmpty(appReplicasDto.getNamespace())) {
            throw new ServerException(CustomEnum.RESET_APPLICATION_REPLICAS_ERROR);
        }
        return applicationService.updateReplicas(appReplicasDto);
    }
}
