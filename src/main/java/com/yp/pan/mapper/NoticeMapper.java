package com.yp.pan.mapper;


import com.yp.pan.model.NoticeInfo;
import com.yp.pan.provider.NoticeProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

public interface NoticeMapper {

    @InsertProvider(type = NoticeProvider.class, method = "addNotice")
    int addNotice(NoticeInfo noticeInfo);

    @SelectProvider(type = NoticeProvider.class, method = "noticeList")
    List<NoticeInfo> noticeList(Map params);

    @SelectProvider(type = NoticeProvider.class, method = "countNotice")
    int countNotice(String userId);

    @UpdateProvider(type = NoticeProvider.class, method = "deleteNotice")
    int deleteNotice(String id);

}
