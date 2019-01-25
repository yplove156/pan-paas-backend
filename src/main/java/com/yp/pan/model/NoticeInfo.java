package com.yp.pan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeInfo {

    private String id;
    private String title;
    private String content;
    private String userId;
    private Long createTime;
    private Long updateTime;
    private Integer deleteFlag;
}
