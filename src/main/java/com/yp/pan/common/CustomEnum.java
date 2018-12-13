package com.yp.pan.common;

/**
 * CustomEnum class
 *
 * @author Administrator
 * @date 2018/07/16
 */
public enum CustomEnum {

    AUTH_FAILED("AUTH_FAILED", "用户认证失败"),

    USERNAME_PASSWORD_EMPTY("USERNAME_PASSWORD_EMPTY", "用户名或密码为空"),

    USERNAME_PASSWORD_ERROR("USERNAME_PASSWORD_ERROR", "用户名或密码错误"),

    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在"),

    ADD_USER_ERROR("ADD_USER_ERROR", "添加成员失败"),

    NAMESPACE_CREATE_ERROR("NAMESPACE_CREATE_ERROR", "命名空间创建失败"),

    NAMESPACE_DELETE_ERROR("NAMESPACE_DELETE_ERROR", "命名空间删除失败"),

//    NAMESPACE_EDIT_ERROR("NAMESPACE_EDIT_ERROR", "命名空间编辑失败"),

    NODE_DETAIL_ERROR("NODE_DETAIL_ERROR", "获取节点详情失败"),

    ADD_CLUSTER_ERROR("ADD_CLUSTER_ERROR", "添加集群信息失败"),
    DELETE_CLUSTER_ERROR("DELETE_CLUSTER_ERROR", "删除集群信息失败"),
    EDIT_CLUSTER_ERROR("EDIT_CLUSTER_ERROR", "修改集群信息失败"),

    /**
     * 出现未知异常
     */
    COMMON_EXCEPTION("COMMON_EXCEPTION", "服务器忙，请稍后重试"),;

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
