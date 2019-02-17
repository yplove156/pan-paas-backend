package com.yp.pan.service;

import com.yp.pan.dto.PvDto;

/**
 * PvService class
 *
 * @author Administrator
 * @date 2019/02/14
 */
public interface PvService {

    Object getAllPv();

    Object createPv(PvDto pvDto);
}
