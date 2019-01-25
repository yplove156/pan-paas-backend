package com.yp.pan.service;

import com.yp.pan.model.NoticeInfo;
import com.yp.pan.model.UserNoticeInfo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface NoticeService {

    List<NoticeInfo> notices(Integer page);

    int send(NoticeInfo noticeInfo);

    List<UserNoticeInfo> userNotices(Integer page);

    List<UserNoticeInfo> notRead();

    int deleteNotice( String id);
}
