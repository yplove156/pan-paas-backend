package com.yp.pan.util;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.RoleEnum;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import org.springframework.util.StringUtils;

public class RoleUtil {

    public static boolean isAdmin(String role) {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        return role.equals(RoleEnum.ADMIN.getRole());
    }

    public static boolean isOwner(ObjectMeta meta, String userId) {
        if (null == meta) {
            return false;
        }
        if (meta.getAnnotations() == null || StringUtils.isEmpty(userId)) {
            return false;
        }
        String ownerId = meta.getAnnotations().get(CustomAnno.PAN_USER);
        if (StringUtils.isEmpty(ownerId)) {
            return false;
        }
        return userId.equals(ownerId);
    }

}
