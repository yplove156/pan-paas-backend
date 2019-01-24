package com.yp.pan.dto;

import io.fabric8.kubernetes.api.model.ServicePort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {

    private String namespace;
    private String name;
    private String type;
    private Map<String, String> selector;
    private List<ServicePort> ports;
}
