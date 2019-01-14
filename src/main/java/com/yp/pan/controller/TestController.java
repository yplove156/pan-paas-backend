package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
public class TestController {
    @Autowired
    ClusterService clusterService;

    @RequestMapping("/test")
    @PanLog("666")
    public Object test() {
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        return userInfo;
    }
}
