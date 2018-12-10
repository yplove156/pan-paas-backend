package com.yp.pan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClusterInfo class
 *
 * @author Administrator
 * @date 2018/12/04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClusterInfo {
    private String id;
    private String url;
    private String caCertData;
    private String clientCertData;
    private String clientKeyData;
    private Integer open;
    private Long createTime;
    private Long updateTime;
    private Integer deleteFlag;
}
