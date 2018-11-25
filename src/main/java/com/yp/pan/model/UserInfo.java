package com.yp.pan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserInfo class
 *
 * @author 年轻的平底锅
 * @date 2018/11/15
 */
@Data
@NoArgsConstructor
public class UserInfo {
    private String id;
    private String username;
    private String nick;
    private String photo;
    private String role;
    private String name;
    private String phone;
    private String position;
    private Long createTime;
    private Long updateTime;
    private Integer deleteFlag;
}
