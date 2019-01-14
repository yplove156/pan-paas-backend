package com.yp.pan.service;

import com.yp.pan.dto.CreateUserDto;
import com.yp.pan.model.UserInfo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * UserService class
 *
 * @author Administrator
 * @date 2018/11/17
 */
public interface UserService {

    Object addUser( CreateUserDto userDto);

    List<UserInfo> userList();
}
