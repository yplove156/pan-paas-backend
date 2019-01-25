package com.yp.pan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNoticeInfo {
    private String id;
    private String title;
    private String content;
    private String sender;
    private String receiver;
    private Integer status;
    private String noticeId;
    private Long createTime;
    private Long updateTime;
    private Integer deleteFlag;
}
