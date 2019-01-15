package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.LogCode;
import com.yp.pan.dto.ImageDto;
import com.yp.pan.model.ImageInfo;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ImageService;
import com.yp.pan.util.Page;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    @PanLog(value = LogCode.ADD_IMAGE_LOG)
    public Object addApplication(@RequestBody ImageInfo imageInfo) {
        int application = imageService.create(imageInfo);
        if (application == 1) {
            return imageInfo;
        }
        throw new ServerException(CustomEnum.ADD_IMAGE_ERROR);
    }

    @GetMapping("/public/{page}")
    public Object pubilcImageList(@PathVariable Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        int limit = 20;
        int total = imageService.publicImageNo();
        if (total == 0) {
            return new Page<ImageDto>(page, limit, 0, total, new ArrayList<>()) {
            };
        }
        int start = (page - 1) * limit;
        int totalPage = (total + limit - 1) / limit;
        List<ImageDto> list = imageService.publicImages(start, limit);
        return new Page<ImageDto>(page, limit, totalPage, total, list) {
        };
    }

    @GetMapping("/private/{page}")
    public Object privateImageList(@PathVariable Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        int limit = 20;
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        int total = imageService.privateImageNo(userInfo.getId());
        if (total == 0) {
            return new Page<ImageDto>(page, limit, 0, total, new ArrayList<>()) {
            };
        }
        int start = (page - 1) * limit;
        int totalPage = (total + limit - 1) / limit;
        List<ImageDto> list = imageService.privateImages(userInfo.getId(), start, limit);
        return new Page<ImageDto>(page, limit, totalPage, total, list) {
        };
    }

    @DeleteMapping("/{id}")
    @PanLog(LogCode.DELETE_IMAGE_LOG)
    public Object deleteApp(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServerException(CustomEnum.DELETE_IMAGE_ERROR);
        }
        return imageService.delete(id);
    }

    @PutMapping
    @PanLog(LogCode.EDIT_IMAGE_LOG)
    public Object updateApp(@RequestBody ImageInfo imageInfo) {
        return imageService.update(imageInfo);
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable String id) {
        return imageService.findById(id);
    }
}
