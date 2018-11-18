package com.yp.pan.controller;

import com.yp.pan.util.UUIDUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public Object test() {
        String salt = UUIDUtil.getUUID(12);
        String passwrod = "panpaas";
        SimpleHash simpleHash = new SimpleHash("md5", passwrod, salt, 2);
        return salt + "---" + simpleHash.toString();
    }
}
