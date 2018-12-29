package com.yp.pan.service;

import com.yp.pan.dto.DeleteAppDto;
import com.yp.pan.dto.DeployDto;
import com.yp.pan.dto.StartAppDto;
import com.yp.pan.dto.StopAppDto;

public interface ApplicationService {

    String deploy(DeployDto deployDto);

    String stopApp(StopAppDto appDto, String userId, String role);

    String startApp(StartAppDto appDto, String userId, String role);

    String deleteApp(DeleteAppDto appDto, String userId, String role);
}
