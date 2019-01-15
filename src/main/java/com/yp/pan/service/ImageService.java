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

    int create(ImageInfo imageInfo);

    int publicImageNo();

    List<ImageDto> publicImages(int start, int limit);

    int privateImageNo(String userId);

    List<ImageDto> privateImages(String userId, int start, int limit);

    String delete(String id);

    String update(ImageInfo imageInfo);

    ImageInfo findById(String id);
}
