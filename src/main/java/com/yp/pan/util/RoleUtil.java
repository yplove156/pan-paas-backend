package com.yp.pan.util;

import com.yp.pan.common.RoleEnum;
import org.springframework.util.StringUtils;

public class RoleUtil {

    public static boolean isAdmin(String role) {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        return role.equals(RoleEnum.ADMIN.getRole());
    }

}
