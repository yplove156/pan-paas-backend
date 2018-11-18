package com.yp.pan.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.util.StringUtils;

/**
 * EncryptUtil class
 *
 * @author Administrator
 * @date 2018/11/17
 */
public class EncryptUtil {
    /**
     * 加密方式
     */
    private final static String METHOD = "md5";
    /**
     * 散列次数
     */
    private final static int ENCRYPT_NO = 2;

    /**
     * 获取加密密码
     *
     * @param pwd
     * @param salt
     * @return
     */
    public static String encryptPwd(String pwd, String salt) {
        if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(salt)) {
            throw new IllegalArgumentException("pwd or salt is empty");
        }
        SimpleHash simpleHash;
        try {
            simpleHash = new SimpleHash(METHOD, pwd, salt, ENCRYPT_NO);
        } catch (Exception e) {
            return null;
        }
        return simpleHash.toString();
    }
}
