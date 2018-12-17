package com.yp.pan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Application class
 *
 * @author Administrator
 * @date 2018/12/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationInfo {

    private String id;
    private String name;
    private String type;
    private String description;
    private String image;
    private String photo;
    private Integer open;
    private String userId;
    private Long createTime;
    private Long updateTime;
    private Integer deleteFlag;
}
