package com.yp.pan.common;

public enum RoleEnum {
    ADMIN("admin"),
    MANAGER("manager");

    RoleEnum(String role) {
        this.role = role;
    }

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
