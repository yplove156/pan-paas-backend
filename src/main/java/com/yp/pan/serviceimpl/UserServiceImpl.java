package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.CreateUserDto;
import com.yp.pan.mapper.PwdMapper;
import com.yp.pan.mapper.UserMapper;
import com.yp.pan.model.PwdInfo;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.UserService;
import com.yp.pan.util.EncryptUtil;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * UserServiceImpl class
 *
 * @author Administrator
 * @date 2018/11/17
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final PwdMapper pwdMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PwdMapper pwdMapper) {
        this.userMapper = userMapper;
        this.pwdMapper = pwdMapper;
    }

    @Override
    public Object addUser(CreateUserDto userDto) {
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
        int addPwd = pwdMapper.addPwd(pwdInfo);
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
            userMapper.addUser(userInfo);
            return userDto;
        }
        throw new ServerException(CustomEnum.ADD_USER_ERROR);
    }

    @Override
    public List<UserInfo> userList() {
        return userMapper.userList();
    }

}
