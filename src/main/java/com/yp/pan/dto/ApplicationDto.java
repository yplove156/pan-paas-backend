package com.yp.pan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ApplicationDto class
 *
 * @author Administrator
 * @date 2018/12/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {
    private String id;
    private String name;
    private String type;
    private String version;
    private String description;
    private String image;
    private String photo;
    private Integer open;
    private Integer level;
    private String userId;
    private Long createTime;
}
