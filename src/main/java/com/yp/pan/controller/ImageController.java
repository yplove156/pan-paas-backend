package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.LogCode;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.dto.ImageDto;
import com.yp.pan.model.ImageInfo;
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
 * ImageController class
 *
 * @author Administrator
 * @date 2018/12/17
 */
@RestController
@RequestMapping("/images")
public class ImageController {

    private final ApplicationService applicationService;

    @Autowired
    public ImageController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PutMapping
    @PanLog(value = LogCode.ADD_IMAGE)
    public Object addApplication(
            @RequestBody ImageInfo imageInfo,
            @RequestAttribute String userId) {
        imageInfo.setId(UUID.randomUUID().toString());
        imageInfo.setUserId(userId);
        imageInfo.setCreateTime(System.currentTimeMillis());
        imageInfo.setUpdateTime(System.currentTimeMillis());
        imageInfo.setDeleteFlag(0);
        int application = applicationService.addApplication(imageInfo);
        if (application == 1) {
            return imageInfo;
        }
        throw new ServerException(CustomEnum.ADD_APPLICATION_ERROR);
    }

    @GetMapping("/{page}")
    @PanLog(value = LogCode.GET_PUBLIC_IMAGES)
    public Object openAppList(@PathVariable Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        int limit = 20;
        int total = applicationService.openAppNo();
        if (total == 0) {
            return new Page<ImageDto>(page, limit, 0, total, new ArrayList<>()) {
            };
        }
        int start = (page - 1) * limit;
        int totalPage = (total + limit - 1) / limit;
        List<ImageDto> list = applicationService.openAppList(start, limit);
        return new Page<ImageDto>(page, limit, totalPage, total, list) {
        };
    }

    @GetMapping("/user/{page}")
    @PanLog("查看私有应用列表")
    public Object openAppList(@RequestAttribute String userId, @PathVariable Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        int limit = 20;
        int total = applicationService.userAppNo(userId);
        if (total == 0) {
            return new Page<ImageDto>(page, limit, 0, total, new ArrayList<>()) {
            };
        }
        int start = (page - 1) * limit;
        int totalPage = (total + limit - 1) / limit;
        List<ImageDto> list = applicationService.userAppList(userId, start, limit);
        return new Page<ImageDto>(page, limit, totalPage, total, list) {
        };
    }

    @DeleteMapping("/{id}")
    @PanLog("删除应用市场应用")
    public Object deleteApp(
            @PathVariable String id,
            @RequestAttribute String userId,
            @RequestAttribute String role) {
        if (StringUtils.isEmpty(id)) {
            throw new ServerException(CustomEnum.DELETE_APPLICATION_ERROR);
        }
        ImageInfo imageInfo = applicationService.getById(id);
        int res = 0;
        if (RoleEnum.ADMIN.getRole().equals(role)) {
            res = applicationService.deleteApp(id);
            if (res == 1) {
                return id;
            }
            throw new ServerException(CustomEnum.DELETE_APPLICATION_ERROR);
        }
        if (imageInfo == null || !userId.equals(imageInfo.getUserId())) {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        res = applicationService.deleteApp(id);
        if (res == 1) {
            return id;
        }
        throw new ServerException(CustomEnum.DELETE_APPLICATION_ERROR);
    }

    @PostMapping
    @PanLog("更新应用市场应用")
    public Object updateApp(
            @RequestBody ImageInfo imageInfo,
            @RequestAttribute String userId,
            @RequestAttribute String role) {
        ImageInfo info = applicationService.getById(imageInfo.getId());
        if (info == null) {
            throw new ServerException(CustomEnum.UPDATE_APPLICATION_ERROR);
        }
        if (RoleEnum.ADMIN.getRole().equals(role)) {
            applicationService.update(imageInfo);
            return imageInfo.getId();
        }
        if (userId.equals(info.getUserId())) {
            applicationService.update(imageInfo);
        } else {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        return imageInfo.getId();
    }
}
