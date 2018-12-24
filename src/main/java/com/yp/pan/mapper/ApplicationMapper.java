package com.yp.pan.mapper;

import com.yp.pan.dto.ApplicationDto;
import com.yp.pan.model.ApplicationInfo;
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
    int addApplication(ApplicationInfo applicationInfo);

    @SelectProvider(type = ApplicationProvider.class, method = "openAppList")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "create_time", property = "createTime")
    })
    List<ApplicationDto> openAppList(Map<String, Object> params);

    @SelectProvider(type = ApplicationProvider.class, method = "openAppNo")
    int openAppNo();

    @SelectProvider(type = ApplicationProvider.class, method = "userAppNo")
    int userAppNo(String userId);

    @SelectProvider(type = ApplicationProvider.class, method = "userAppList")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "create_time", property = "createTime")
    })
    List<ApplicationDto> userAppList(Map<String, Object> params);

    @UpdateProvider(type = ApplicationProvider.class, method = "deleteApp")
    int deleteApp(String id);
}
