package com.yp.pan.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yp.pan.model.UserInfo;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TokenUtil class
 *
 * @author 年轻的平底锅
 * @date 2018/11/15
 */
public class TokenUtil {
    private static final String SECRET = "pan_paas_secret";
    //发布者 后面一块去校验
    private static final String ISSUER = "pan_paas_user";

    public static String getToken(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        try {
            //签名算法
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            LocalDateTime time = LocalDateTime.now();
            Date date = new Date(time.plusDays(3).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withExpiresAt(date);
            builder.withClaim("userId", userInfo.getId());
            builder.withClaim("role", userInfo.getRole());
            return builder.sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, String> decodeToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> map = jwt.getClaims();
            Map<String, String> resultMap = new HashMap<>();
            map.forEach((k, v) -> resultMap.put(k, v.asString()));
            resultMap.put("expireTime", jwt.getExpiresAt().getTime() + "");
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }
}
