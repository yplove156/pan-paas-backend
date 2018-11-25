package com.yp.pan.serviceimpl;

import com.yp.pan.mapper.UserMapper;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserInfo findByUsername(String username) throws Exception {
        return userMapper.findByUsername(username);
    }

    @Override
    public List<UserInfo> userList() throws Exception {
        return userMapper.userList();
    }

    @Override
    public int addUser(UserInfo userInfo) throws Exception {
        return userMapper.addUser(userInfo);
    }
}
