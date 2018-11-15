package com.yp.pan.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yp.pan.model.UserInfo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * TokenUtil class
 *
 * @author 年轻的平底锅
 * @date 2018/11/15
 */
public class TokenUtil {

    public static String getToken(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        try {
            LocalDateTime time = LocalDateTime.now();
            Date date = new Date(time.plusDays(3).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            return JWT.create().withAudience(userInfo.getId()).withExpiresAt(date)
                    .sign(Algorithm.HMAC256(userInfo.getId()));
        } catch (Exception e) {
            return null;
        }

    }
}
