package com.yp.pan.controller;

import com.yp.pan.dto.LoginDto;
import com.yp.pan.dto.UserInfoDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController class
 *
 * @author Administrator
 * @date 2018/11/15
 */
@RestController
public class LoginController {

    @RequestMapping("/login")
    public UserInfoDto login(@RequestBody LoginDto loginDto) throws Exception{
        return null;
    }

    @RequestMapping("/test")
    public Object test(){
        return "00";
    }
}
