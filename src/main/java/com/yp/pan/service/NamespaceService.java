package com.yp.pan.service;

import com.yp.pan.dto.NamespaceDto;

public interface NamespaceService {

    Object namespaces();

    Object userNamespace();

    Object addNamespace(NamespaceDto namespaceDto);

    Object deleteNamespace(String name);
}
