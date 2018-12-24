package com.yp.pan.controller;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.ApplicationDto;
import com.yp.pan.model.ApplicationInfo;
import com.yp.pan.service.ApplicationService;
import com.yp.pan.util.Page;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ApplicationController class
 *
 * @author Administrator
 * @date 2018/12/17
 */
@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PutMapping
    public Object addApplication(@RequestBody ApplicationInfo applicationInfo, @RequestAttribute String userId) {
        applicationInfo.setId(UUID.randomUUID().toString());
        applicationInfo.setUserId(userId);
        applicationInfo.setCreateTime(System.currentTimeMillis());
        applicationInfo.setUpdateTime(System.currentTimeMillis());
        applicationInfo.setDeleteFlag(0);
        int application = applicationService.addApplication(applicationInfo);
        if (application == 1) {
            return applicationInfo;
        }
        throw new ServerException(CustomEnum.ADD_APPLICATION_ERROR);
    }

    @GetMapping("/{page}")
    public Object openAppList(@PathVariable Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        int limit = 20;
        int total = applicationService.openAppNo();
        if (total == 0) {
            return new Page<ApplicationDto>(page, limit, 0, total, new ArrayList<>()) {
            };
        }
        int start = (page - 1) * limit;
        int totalPage = (total + limit - 1) / limit;
        List<ApplicationDto> list = applicationService.openAppList(start, limit);
        return new Page<ApplicationDto>(page, limit, totalPage, total, list) {
        };
    }

    @GetMapping("/user/{page}")
    public Object openAppList(@RequestAttribute String userId, @PathVariable Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        int limit = 20;
        int total = applicationService.userAppNo(userId);
        if (total == 0) {
            return new Page<ApplicationDto>(page, limit, 0, total, new ArrayList<>()) {
            };
        }
        int start = (page - 1) * limit;
        int totalPage = (total + limit - 1) / limit;
        List<ApplicationDto> list = applicationService.userAppList(userId, start, limit);
        return new Page<ApplicationDto>(page, limit, totalPage, total, list) {
        };
    }

    @DeleteMapping("/{id}")
    public Object deleteApp(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServerException(CustomEnum.DELETE_APPLICATION_ERROR);
        }
        int res = applicationService.deleteApp(id);
        if (res == 1) {
            return id;
        }
        throw new ServerException(CustomEnum.DELETE_APPLICATION_ERROR);
    }
}
