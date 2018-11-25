package com.yp.pan.controller;

import com.yp.pan.annotation.RequireRole;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.LoginDto;
import com.yp.pan.dto.UserInfoDto;
import com.yp.pan.model.PwdInfo;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.PwdService;
import com.yp.pan.service.UserService;
import com.yp.pan.util.EncryptUtil;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
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

    private final PwdService pwdService;
    private final UserService userService;

    @Autowired
    public LoginController(PwdService pwdService, UserService userService) {
        this.pwdService = pwdService;
        this.userService = userService;
    }

    @RequireRole("admin")
    @PostMapping("/login")
    public Object login(@RequestBody LoginDto loginDto) throws Exception {
        if (StringUtils.isEmpty(loginDto.getUsername()) || StringUtils.isEmpty(loginDto.getPassword())) {
            throw new ServerException(CustomEnum.USERNAME_PASSWORD_EMPTY);
        }
        PwdInfo pwdInfo = pwdService.findByUsername(loginDto.getUsername());
        if (pwdInfo == null) {
            throw new ServerException(CustomEnum.USERNAME_PASSWORD_ERROR);
        }
        String encryptPwd = EncryptUtil.encryptPwd(loginDto.getPassword(), pwdInfo.getSalt());
        if (StringUtils.isEmpty(encryptPwd)) {
            throw new ServerException(CustomEnum.COMMON_EXCEPTION);
        }
        if (encryptPwd.equals(pwdInfo.getPassword())) {
            UserInfo userInfo = userService.findByUsername(loginDto.getUsername());
            if (userInfo == null) {
                throw new ServerException(CustomEnum.USER_NOT_EXIST);
            }
            String token = TokenUtil.getToken(userInfo);
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setUsername(userInfo.getUsername());
            userInfoDto.setNick(userInfo.getNick());
            userInfoDto.setPhoto(userInfo.getPhoto());
            userInfoDto.setRole(userInfo.getRole());
            userInfoDto.setName(userInfo.getName());
            userInfoDto.setPhone(userInfo.getPhone());
            userInfoDto.setPosition(userInfo.getPosition());
            userInfoDto.setToken(token);
            return userInfoDto;
        }
        throw new ServerException(CustomEnum.USERNAME_PASSWORD_ERROR);
    }

}
