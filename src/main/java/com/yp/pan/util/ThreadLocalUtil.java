package com.yp.pan.util;

import com.yp.pan.model.UserInfo;

public class ThreadLocalUtil {
    private ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();

    //new一个实例
    private static final ThreadLocalUtil instance = new ThreadLocalUtil();

    //私有化构造
    private ThreadLocalUtil() {
    }

    //获取单例
    public static ThreadLocalUtil getInstance() {
        return instance;
    }

    /**
     * 将用户对象绑定到当前线程中，键为userInfoThreadLocal对象，值为userInfo对象
     *
     * @param userInfo
     */
    public void bind(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    /**
     * 得到绑定的用户对象
     *
     * @return
     */
    public UserInfo getUserInfo() {
        UserInfo userInfo = userInfoThreadLocal.get();
        remove();
        return userInfo;
    }

    /**
     * 移除绑定的用户对象
     */
    public void remove() {
        userInfoThreadLocal.remove();
    }

}
