package com.yp.pan.common;

import com.alibaba.fastjson.JSONObject;
import com.yp.pan.util.ResponseEntity;
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

    private final static String LOGIN_URL = "/login";
    private final static String LOGOUT_URL = "/logout";
    private final static String NULL_STR = "null";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        if (LOGIN_URL.equals(url)) {
            return true;
        }
        String token = request.getHeader("pan-tk");
        if (StringUtils.isEmpty(token) || NULL_STR.equals(token)) {
            String res = JSONObject.toJSONString(ResponseEntity.getFail(CustomEnum.AUTH_FAILED.getCode(), CustomEnum.AUTH_FAILED.getMsg()));
            response.getWriter().write(res);
            return false;
        }
        Map<String, String> datas = TokenUtil.decodeToken(token);
        if (datas == null) {
            String res = JSONObject.toJSONString(ResponseEntity.getFail(CustomEnum.AUTH_FAILED.getCode(), CustomEnum.AUTH_FAILED.getMsg()));
            response.getWriter().write(res);
            return false;
        }
        Long expireTime = Long.parseLong(datas.get("expireTime"));
        if (expireTime < System.currentTimeMillis()) {
            String res = JSONObject.toJSONString(ResponseEntity.getFail(CustomEnum.AUTH_FAILED.getCode(), CustomEnum.AUTH_FAILED.getMsg()));
            response.getWriter().write(res);
            return false;
        }
        datas.forEach(request::setAttribute);
        return true;
    }
}