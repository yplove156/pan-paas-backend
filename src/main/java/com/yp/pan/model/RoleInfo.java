package com.yp.pan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RoleInfo class
 *
 * @author 年轻的平底锅
 * @date 2018/11/15
 */
@Data
@NoArgsConstructor
public class RoleInfo {
    private String id;
    private String role;
    private String desc;
    private Long createTime;
    private Long updateTime;
    private Integer deleteFlag;
}
