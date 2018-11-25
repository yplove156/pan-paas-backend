package com.yp.pan.service;

import com.yp.pan.model.UserInfo;

import java.util.List;

/**
 * UserService class
 *
 * @author Administrator
 * @date 2018/11/17
 */
public interface UserService {

    UserInfo findByUsername(String username) throws Exception;

    List<UserInfo> userList() throws Exception;

    int addUser(UserInfo userInfo) throws Exception;
}
