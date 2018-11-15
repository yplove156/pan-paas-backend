package com.yp.pan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PwdInfo class
 *
 * @author Administrator
 * @date 2018/11/15
 */
@Data
@NoArgsConstructor
public class PwdInfo {
    private String id;
    private String username;
    private String password;
    private String salt;
    private String state;
    private Long createTime;
    private Long updateTime;
    private Integer deleteFlag;
}
