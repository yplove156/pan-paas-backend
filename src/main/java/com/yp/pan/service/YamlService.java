package com.yp.pan.service;

import java.io.InputStream;

public interface YamlService {

    Object deployByYaml(InputStream inputStream);

    Object deleteByYaml(InputStream inputStream);
}
