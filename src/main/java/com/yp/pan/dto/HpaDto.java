package com.yp.pan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HpaDto {

    private String name;
    private String namespace;
    private Integer minReplicas;
    private Integer maxReplicas;
    private Integer cpuPercent;
    private Integer memoryPercent;
}
