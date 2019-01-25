package com.yp.pan.mapper;

import com.yp.pan.model.UserNoticeInfo;
import com.yp.pan.provider.UserNoticeProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

public interface UserNoticeMapper {

    @SelectProvider(type = UserNoticeProvider.class, method = "userNoticeList")
    List<UserNoticeInfo> userNoticeList(Map params);

    @SelectProvider(type = UserNoticeProvider.class, method = "userNotReadNoticeList")
    List<UserNoticeInfo> userNotReadNoticeList(String userId);

    @SelectProvider(type = UserNoticeProvider.class, method = "countUserNotice")
    int countUserNotice(String userId);

    @UpdateProvider(type = UserNoticeProvider.class, method = "addUserNotice")
    int addUserNotice(List<UserNoticeInfo> userNoticeInfos);

    @UpdateProvider(type = UserNoticeProvider.class, method = "deleteUserNotice")
    int deleteUserNotice(String id);

    @UpdateProvider(type = UserNoticeProvider.class, method = "deleteByNoticeId")
    int deleteByNoticeId(List<String> ids);

}
