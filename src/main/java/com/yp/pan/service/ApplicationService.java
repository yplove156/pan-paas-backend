package com.yp.pan.service;

import com.yp.pan.dto.ApplicationDto;
import com.yp.pan.model.ApplicationInfo;

import java.util.List;

/**
 * ApplicationService class
 *
 * @author Administrator
 * @date 2018/12/17
 */
public interface ApplicationService {

    int addApplication(ApplicationInfo applicationInfo);

    int openAppNo();

    List<ApplicationDto> openAppList(int start, int limit);

    int userAppNo(String userId);

    List<ApplicationDto> userAppList(String userId, int start, int limit);
}
