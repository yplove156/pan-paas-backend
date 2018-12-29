package com.yp.pan.service;

import com.yp.pan.dto.ImageDto;
import com.yp.pan.model.ImageInfo;

import java.util.List;

/**
 * ImageService class
 *
 * @author Administrator
 * @date 2018/12/17
 */
public interface ImageService {

    int addApplication(ImageInfo imageInfo);

    int openAppNo();

    List<ImageDto> openAppList(int start, int limit);

    int userAppNo(String userId);

    List<ImageDto> userAppList(String userId, int start, int limit);

    int deleteApp(String id);

    int update(ImageInfo imageInfo);

    ImageInfo getById(String id);
}
