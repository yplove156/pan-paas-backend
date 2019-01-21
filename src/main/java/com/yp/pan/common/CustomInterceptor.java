package com.yp.pan.common;

import com.alibaba.fastjson.JSONObject;
import com.yp.pan.model.UserInfo;
import com.yp.pan.util.ResponseEntity;
import com.yp.pan.util.ThreadLocalUtil;
import com.yp.pan.util.TokenUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * CustomInterceptor class
 *
 * @author Administrator
 * @date 2018/11/20
 */
public class CustomInterceptor implements HandlerInterceptor {

    private final static String NULL_STR = "null";
    private final static String PAN_TOKEN = "pan-tk";
    private final static String EXPIRE_TIME = "expireTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(PAN_TOKEN);
        if (StringUtils.isEmpty(token) || NULL_STR.equals(token)) {
            String res = JSONObject.toJSONString(ResponseEntity.getFail(CustomEnum.AUTH_FAILED.getCode(), CustomEnum.AUTH_FAILED.getMsg()));
            response.getWriter().write(res);
            return false;
        }
        Map<String, String> data = TokenUtil.decodeToken(token);
        if (data == null) {
            String res = JSONObject.toJSONString(ResponseEntity.getFail(CustomEnum.AUTH_FAILED.getCode(), CustomEnum.AUTH_FAILED.getMsg()));
            response.getWriter().write(res);
            return false;
        }
        Long expireTime = Long.parseLong(data.get(EXPIRE_TIME));
        if (expireTime < System.currentTimeMillis()) {
            String res = JSONObject.toJSONString(ResponseEntity.getFail(CustomEnum.AUTH_FAILED.getCode(), CustomEnum.AUTH_FAILED.getMsg()));
            response.getWriter().write(res);
            return false;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(data.get("userId"));
        userInfo.setUsername(data.get("username"));
        userInfo.setRole(data.get("role"));
        ThreadLocalUtil.getInstance().bind(userInfo);
        return true;
    }
}
