package com.yp.pan.mapper;

import com.yp.pan.dto.ImageDto;
import com.yp.pan.model.ImageInfo;
import com.yp.pan.provider.ApplicationProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * ApplicationMapper class
 *
 * @author Administrator
 * @date 2018/12/13
 */
public interface ApplicationMapper {

    @InsertProvider(type = ApplicationProvider.class, method = "addApplication")
    int addApplication(ImageInfo imageInfo);

    @SelectProvider(type = ApplicationProvider.class, method = "openAppList")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "create_time", property = "createTime")
    })
    List<ImageDto> openAppList(Map<String, Object> params);

    @SelectProvider(type = ApplicationProvider.class, method = "openAppNo")
    int openAppNo();

    @SelectProvider(type = ApplicationProvider.class, method = "userAppNo")
    int userAppNo(String userId);

    @SelectProvider(type = ApplicationProvider.class, method = "userAppList")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "create_time", property = "createTime")
    })
    List<ImageDto> userAppList(Map<String, Object> params);

    @UpdateProvider(type = ApplicationProvider.class, method = "deleteApp")
    int deleteApp(String id);

    @UpdateProvider(type = ApplicationProvider.class, method = "update")
    int update(ImageInfo imageInfo);

    @SelectProvider(type = ApplicationProvider.class,method = "getById")
    ImageInfo getById(String id);
}
