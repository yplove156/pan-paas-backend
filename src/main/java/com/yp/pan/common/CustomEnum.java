package com.yp.pan.common;

/**
 * CustomEnum class
 *
 * @author Administrator
 * @date 2018/07/16
 */
public enum CustomEnum {
    /**
     * 正常返回数据
     */
    COMMON_SUCCESS("0", ""),
    USERNAME_PASSWORD_EMPTY("10001", "用户名或密码为空"),
    USERNAME_PASSWORD_ERROR("10002", "用户名或密码错误"),
    USER_NOT_EXIST("10003", "用户不存在"),
    /**
     * 出现未知异常
     */
    COMMON_EXCEPTION("1", "服务器忙，请稍后重试"),;

    private String code;
    private String msg;

    CustomEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
