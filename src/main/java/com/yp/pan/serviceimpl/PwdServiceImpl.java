package com.yp.pan.serviceimpl;

import com.yp.pan.mapper.PwdMapper;
import com.yp.pan.model.PwdInfo;
import com.yp.pan.service.PwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PwdServiceImpl class
 *
 * @author Administrator
 * @date 2018/11/17
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class PwdServiceImpl implements PwdService {
    private final PwdMapper pwdMapper;

    @Autowired
    public PwdServiceImpl(PwdMapper pwdMapper) {
        this.pwdMapper = pwdMapper;
    }

    @Override
    public PwdInfo findByUsername(String username) {
        return pwdMapper.findByUsername(username);
    }

    @Override
    public int addPwd(PwdInfo pwdInfo) {
        return pwdMapper.addPwd(pwdInfo);
    }
}
