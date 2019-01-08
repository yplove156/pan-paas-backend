package com.yp.pan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LogInfo class
 *
 * @author Administrator
 * @date 2018/12/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInfo {

    private String id;
    private String action;
    private String user;
    private Integer status;
    private String res;
    private String exception;
    private String code;
    private String message;
    private Long createTime;
}
