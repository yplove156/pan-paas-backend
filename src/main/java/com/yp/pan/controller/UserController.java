package com.yp.pan.controller;

import com.yp.pan.annotation.RequireRole;
import com.yp.pan.dto.CreateUserDto;
import com.yp.pan.dto.UserInfoDto;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * UserController class
 *
 * @author Administrator
 * @date 2018/11/25
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    @RequireRole("admin")
    public Object addUser(@RequestBody CreateUserDto userDto) {
        return userService.addUser(userDto);
    }

    @GetMapping
    @RequireRole("admin")
    public Object userList() {
        List<UserInfo> userInfos = userService.userList();
        List<UserInfoDto> userInfoDtos = new ArrayList<>();
        userInfos.forEach(userInfo -> {
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setUsername(userInfo.getUsername());
            userInfoDto.setNick(userInfo.getNick());
            userInfoDto.setPosition(userInfo.getPosition());
            userInfoDto.setPhone(userInfo.getPhone());
            userInfoDto.setRole(userInfo.getRole());
            userInfoDto.setName(userInfo.getName());
            userInfoDto.setPhoto(userInfo.getPhoto());
            userInfoDtos.add(userInfoDto);
        });
        return userInfoDtos;
    }
}
