package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.LogCode;
import com.yp.pan.dto.ImageDto;
import com.yp.pan.model.ImageInfo;
import com.yp.pan.service.ImageService;
import com.yp.pan.util.Page;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * ImageController class
 *
 * @author Administrator
 * @date 2018/12/17
 */
@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    @PanLog(value = LogCode.ADD_IMAGE)
    public Object addApplication(@RequestBody ImageInfo imageInfo) {
        int application = imageService.addApplication(imageInfo);
        if (application == 1) {
            return imageInfo;
        }
        throw new ServerException(CustomEnum.ADD_IMAGE_ERROR);
    }

    @GetMapping("/{page}")
    public Object pubilcImageList(@PathVariable Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        int limit = 20;
        int total = imageService.openAppNo();
        if (total == 0) {
            return new Page<ImageDto>(page, limit, 0, total, new ArrayList<>()) {
            };
        }
        int start = (page - 1) * limit;
        int totalPage = (total + limit - 1) / limit;
        List<ImageDto> list = imageService.openAppList(start, limit);
        return new Page<ImageDto>(page, limit, totalPage, total, list) {
        };
    }

    @GetMapping("/user/{page}")
    public Object privateImageList(@RequestAttribute String userId, @PathVariable Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        int limit = 20;
        int total = imageService.userAppNo(userId);
        if (total == 0) {
            return new Page<ImageDto>(page, limit, 0, total, new ArrayList<>()) {
            };
        }
        int start = (page - 1) * limit;
        int totalPage = (total + limit - 1) / limit;
        List<ImageDto> list = imageService.userAppList(userId, start, limit);
        return new Page<ImageDto>(page, limit, totalPage, total, list) {
        };
    }

    @DeleteMapping("/{id}")
    @PanLog(LogCode.DELETE_IMAGE)
    public Object deleteApp(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServerException(CustomEnum.DELETE_IMAGE_ERROR);
        }
        return imageService.deleteApp(id);
    }

    @PostMapping
    @PanLog(LogCode.EDIT_IMAGE)
    public Object updateApp(@RequestBody ImageInfo imageInfo) {
        return imageService.update(imageInfo);
    }
}
