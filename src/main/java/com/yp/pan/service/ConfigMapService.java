package com.yp.pan.service;

import com.yp.pan.dto.ConfigMapDto;

public interface ConfigMapService {

    Object configMapList();

    Object createConfigMap(ConfigMapDto configMapDto);

    String deleteConfigMap(String namespace, String name);
}
