package com.yp.pan.service;

import com.yp.pan.dto.LoginDto;
import com.yp.pan.dto.UserInfoDto;

/**
 * LoginService class
 *
 * @author Administrator
 * @date 2018/11/17
 */
public interface LoginService {

    UserInfoDto login(LoginDto loginDto);
}
