package com.yp.pan.serviceimpl;

import com.yp.pan.model.NoticeInfo;
import com.yp.pan.model.UserNoticeInfo;
import com.yp.pan.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Override
    public List<NoticeInfo> notices(Integer page) {
        return null;
    }

    @Override
    public int send(NoticeInfo noticeInfo) {
        return 0;
    }

    @Override
    public List<UserNoticeInfo> userNotices(Integer page) {
        return null;
    }

    @Override
    public List<UserNoticeInfo> notRead() {
        return null;
    }

    @Override
    public int deleteNotice(String id) {
        return 0;
    }
}
