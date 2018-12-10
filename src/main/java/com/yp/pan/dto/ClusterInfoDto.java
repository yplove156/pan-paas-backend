package com.yp.pan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * ClusterInfoDto class
 *
 * @author Administrator
 * @date 2018/12/07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClusterInfoDto {

    private String id;
    private String url;
    private String caCertData;
    private String clientCertData;
    private String clientKeyData;
    private Integer open;
    private Long createTime;
}
