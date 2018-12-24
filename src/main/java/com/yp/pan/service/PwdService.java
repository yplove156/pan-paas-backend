package com.yp.pan.service;

import com.yp.pan.model.PwdInfo;

import java.util.concurrent.CompletableFuture;

/**
 * PwdService class
 *
 * @author Administrator
 * @date 2018/11/17
 */
public interface PwdService {
    /**
     * 根据用户名获取密码信息
     *
     * @param username
     * @return
     */
    PwdInfo findByUsername(String username);

    int addPwd(PwdInfo pwdInfo);
}
