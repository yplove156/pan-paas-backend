package com.yp.pan.common;

/**
 * CustomEnum class
 *
 * @author Administrator
 * @date 2018/07/16
 */
public enum CustomEnum {

    AUTH_FAILED("AUTH_FAILED", "用户认证失败"),
    NO_PERMISSION("NO_PERMISSION", "您无权执行此操作"),
    INIT_CLUSTER_ERROR("INIT_CLUSTER_ERROR", "连接集群失败，请检查连接配置"),

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
    ADD_IMAGE_ERROR("ADD_IMAGE_ERROR", "添加镜像信息失败"),
    DELETE_IMAGE_ERROR("DELETE_IMAGE_ERROR", "删除镜像信息失败"),
    UPDATE_IMAGE_ERROR("UPDATE_IMAGE_ERROR", "更新镜像信息失败"),
    GET_IMAGE_ERROR("GET_IMAGE_ERROR", "镜像不存在或已删除"),

    DEPLOY_APPLICATION_ERROR("DEPLOY_APPLICATION_ERROR", "部署应用失败"),
    STOP_APPLICATION_ERROR("STOP_APPLICATION_ERROR", "停止应用失败"),
    START_APPLICATION_ERROR("START_APPLICATION_ERROR", "启动应用失败"),
    DELETE_APPLICATION_ERROR("DELETE_APPLICATION_ERROR", "删除应用失败"),
    RESET_APPLICATION_REPLICAS_ERROR("RESET_APPLICATION_REPLICAS_ERROR", "设置应用副本失败"),

    DELETE_SERVICE_ERROR("DELETE_SERVICE_ERROR", "删除Service失败"),

    NEW_PWD_NOT_EQUALS("NEW_PWD_NOT_EQUALS", "新密码不相同"),
    UPDATE_PWD_ERROR("UPDATE_PWD_ERROR", "修改密码失败"),

    YAML_DEPLOY_ERROR("YAML_DEPLOY_ERROR", "Yaml部署失败"),

    POD_REBOOT_ERROR("POD_REBOOT_ERROR", "实例重启失败"),
    CONFIG_MAP_DELETE_ERROR("CONFIG_MAP_DELETE_ERROR", "ConfigMap删除失败"),

    /**
     * 出现未知异常
     */
    COMMON_EXCEPTION("COMMON_EXCEPTION", "未知错误，请稍后重试"),;

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
