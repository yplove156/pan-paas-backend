package com.yp.pan.service;

import com.yp.pan.dto.ServiceDto;

public interface SVCService {

    Object services();

    Object deleteService(String namespace, String name);

    Object addService(ServiceDto serviceDto);
}
