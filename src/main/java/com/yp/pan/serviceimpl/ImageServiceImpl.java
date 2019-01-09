package com.yp.pan.serviceimpl;

import com.yp.pan.dto.ImageDto;
import com.yp.pan.mapper.ImageMapper;
import com.yp.pan.model.ImageInfo;
import com.yp.pan.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ImageServiceImpl class
 *
 * @author Administrator
 * @date 2018/12/17
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageMapper applicationMapper;

    @Autowired
    public ImageServiceImpl(ImageMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }

    @Override
    public int addApplication(ImageInfo imageInfo) {
        return applicationMapper.addApplication(imageInfo);
    }

    @Override
    public int openAppNo() {
        return applicationMapper.openAppNo();
    }

    @Override
    public List<ImageDto> openAppList(int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("limit", limit);
        return applicationMapper.openAppList(params);
    }

    @Override
    public int userAppNo(String userId) {
        return applicationMapper.userAppNo(userId);
    }

    @Override
    public List<ImageDto> userAppList(String userId, int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("start", start);
        params.put("limit", limit);
        return applicationMapper.userAppList(params);
    }

    @Override
    public int deleteApp(String id) {
        return applicationMapper.deleteApp(id);
    }

    @Override
    public int update(ImageInfo imageInfo) {
        return applicationMapper.update(imageInfo);
    }

    @Override
    public ImageInfo getById(String id) {
        return applicationMapper.getById(id);
    }
}
