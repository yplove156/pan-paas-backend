package com.yp.pan.service;

import com.yp.pan.model.UserInfo;

/**
 * UserService class
 *
 * @author Administrator
 * @date 2018/11/17
 */
public interface UserService {

    UserInfo findByUsername(String username) throws Exception;
}
