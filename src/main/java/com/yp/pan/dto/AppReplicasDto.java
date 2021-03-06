package com.yp.pan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppReplicasDto {

    private String namespace;
    private String name;
    private Integer replicas;
}
