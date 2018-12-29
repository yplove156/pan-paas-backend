package com.yp.pan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeployDto {

    private String imageId;
    private String namespace;
    private Integer replicas;
    private String imagePullPolicy;
}
