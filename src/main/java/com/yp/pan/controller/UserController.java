package com.yp.pan.controller;

import com.yp.pan.annotation.RequireRole;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.CreateUserDto;
import com.yp.pan.dto.UserInfoDto;
import com.yp.pan.model.PwdInfo;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.PwdService;
import com.yp.pan.service.UserService;
import com.yp.pan.util.EncryptUtil;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private final PwdService pwdService;

    @Autowired
    public UserController(UserService userService, PwdService pwdService) {
        this.userService = userService;
        this.pwdService = pwdService;
    }

    @PutMapping
    @RequireRole("admin")
    public Object addUser(@RequestBody CreateUserDto userDto) throws Exception {
        String salt = UUIDUtil.getUUID(12);
        String encryptPwd = EncryptUtil.encryptPwd(userDto.getPassword(), salt);
        PwdInfo pwdInfo = new PwdInfo();
        pwdInfo.setId(UUID.randomUUID().toString());
        pwdInfo.setSalt(salt);
        pwdInfo.setUsername(userDto.getUsername());
        pwdInfo.setPassword(encryptPwd);
        pwdInfo.setState("0");
        pwdInfo.setCreateTime(System.currentTimeMillis());
        pwdInfo.setUpdateTime(System.currentTimeMillis());
        pwdInfo.setDeleteFlag(0);
        int addPwd = pwdService.addPwd(pwdInfo);
        if (addPwd == 1) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(UUID.randomUUID().toString());
            userInfo.setUsername(userDto.getUsername());
            userInfo.setNick(userDto.getNick());
            userInfo.setPhoto(userDto.getPhoto());
            userInfo.setRole(userDto.getRole());
            userInfo.setName(userDto.getName());
            userInfo.setPhone(userDto.getPhone());
            userInfo.setPosition(userDto.getPosition());
            userInfo.setCreateTime(System.currentTimeMillis());
            userInfo.setUpdateTime(System.currentTimeMillis());
            userInfo.setDeleteFlag(0);
            userService.addUser(userInfo);
            return userDto;
        }
        throw new ServerException(CustomEnum.ADD_USER_ERROR);
    }

    @GetMapping
    @RequireRole("admin")
    public Object userList() throws Exception {
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
