package com.yp.pan.service;

import java.util.Map;

public interface ConfigMapService {

    Object configMapList();

    Object createConfigMap(Map<String, String> data);
}
