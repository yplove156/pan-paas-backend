package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.LoginDto;
import com.yp.pan.dto.UserInfoDto;
import com.yp.pan.mapper.PwdMapper;
import com.yp.pan.mapper.UserMapper;
import com.yp.pan.model.PwdInfo;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.LoginService;
import com.yp.pan.util.EncryptUtil;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * LoginServiceImpl class
 *
 * @author Administrator
 * @date 2018/11/17
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class LoginServiceImpl implements LoginService {
    private final PwdMapper pwdMapper;

    private final UserMapper userMapper;

    @Autowired
    public LoginServiceImpl(PwdMapper pwdMapper, UserMapper userMapper) {
        this.pwdMapper = pwdMapper;
        this.userMapper = userMapper;
    }

    @Override
    public UserInfoDto login(LoginDto loginDto) {
        PwdInfo pwdInfo = pwdMapper.findByUsername(loginDto.getUsername());
        if (pwdInfo == null) {
            throw new ServerException(CustomEnum.USERNAME_PASSWORD_ERROR);
        }
        String encryptPwd = EncryptUtil.encryptPwd(loginDto.getPassword(), pwdInfo.getSalt());
        if (StringUtils.isEmpty(encryptPwd)) {
            throw new ServerException(CustomEnum.COMMON_EXCEPTION);
        }
        if (encryptPwd.equals(pwdInfo.getPassword())) {
            UserInfo userInfo = userMapper.findByUsername(loginDto.getUsername());
            if (userInfo == null) {
                throw new ServerException(CustomEnum.USERNAME_PASSWORD_ERROR);
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
