package com.yp.pan.controller;

import com.yp.pan.annotation.RequireRole;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.LoginDto;
import com.yp.pan.service.LoginService;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController class
 *
 * @author Administrator
 * @date 2018/11/15
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequireRole("admin")
    @PostMapping("/login")
    public Object login(@RequestBody LoginDto loginDto) {
        if (StringUtils.isEmpty(loginDto.getUsername()) || StringUtils.isEmpty(loginDto.getPassword())) {
            throw new ServerException(CustomEnum.USERNAME_PASSWORD_EMPTY);
        }
        return loginService.login(loginDto);
    }

}
