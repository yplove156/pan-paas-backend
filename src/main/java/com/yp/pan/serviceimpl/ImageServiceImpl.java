package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.dto.ImageDto;
import com.yp.pan.mapper.ImageMapper;
import com.yp.pan.model.ImageInfo;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ImageService;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ImageServiceImpl class
 *
 * @author Administrator
 * @date 2018/12/17
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageMapper imageMapper;

    @Autowired
    public ImageServiceImpl(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    @Override
    public int addApplication(ImageInfo imageInfo) {
        imageInfo.setId(UUID.randomUUID().toString());
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        imageInfo.setUserId(userInfo.getId());
        imageInfo.setUsername(userInfo.getUsername());
        imageInfo.setCreateTime(System.currentTimeMillis());
        imageInfo.setUpdateTime(System.currentTimeMillis());
        imageInfo.setDeleteFlag(0);
        return imageMapper.addApplication(imageInfo);
    }

    @Override
    public int openAppNo() {
        return imageMapper.openAppNo();
    }

    @Override
    public List<ImageDto> openAppList(int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("limit", limit);
        return imageMapper.openAppList(params);
    }

    @Override
    public int userAppNo(String userId) {
        return imageMapper.userAppNo(userId);
    }

    @Override
    public List<ImageDto> userAppList(String userId, int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("start", start);
        params.put("limit", limit);
        return imageMapper.userAppList(params);
    }

    @Override
    public String deleteApp(String id) {
        ImageInfo imageInfo = imageMapper.getById(id);
        int res = 0;
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        if (RoleEnum.ADMIN.getRole().equals(userInfo.getRole())) {
            res = imageMapper.deleteApp(id);
            if (res == 1) {
                return id;
            }
            throw new ServerException(CustomEnum.DELETE_IMAGE_ERROR);
        }
        if (imageInfo == null || !userInfo.getId().equals(imageInfo.getUserId())) {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        res = imageMapper.deleteApp(id);
        if (res == 1) {
            return id;
        }
        throw new ServerException(CustomEnum.DELETE_IMAGE_ERROR);
    }

    @Override
    public String update(ImageInfo imageInfo) {
        ImageInfo info = imageMapper.getById(imageInfo.getId());
        if (info == null) {
            throw new ServerException(CustomEnum.UPDATE_IMAGE_ERROR);
        }
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        if (RoleEnum.ADMIN.getRole().equals(userInfo.getRole())) {
            imageMapper.update(imageInfo);
            return imageInfo.getId();
        }
        if (userInfo.getId().equals(info.getUserId())) {
            imageMapper.update(imageInfo);
        } else {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        return imageInfo.getId();
    }

    @Override
    public ImageInfo getById(String id) {
        return imageMapper.getById(id);
    }
}
