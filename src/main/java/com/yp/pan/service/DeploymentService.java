package com.yp.pan.service;

import com.yp.pan.dto.AppReplicasDto;
import com.yp.pan.dto.DeleteAppDto;
import com.yp.pan.dto.DeployDto;
import com.yp.pan.dto.HpaDto;
import com.yp.pan.dto.StartAppDto;
import com.yp.pan.dto.StopAppDto;

public interface DeploymentService {

    Object apps(String namespace);

    String deploy(DeployDto deployDto);

    String stopApp(StopAppDto appDto);

    String startApp(StartAppDto appDto);

    String deleteApp(DeleteAppDto appDto);

    Object updateReplicas(AppReplicasDto appReplicasDto);

    Object autoScale(HpaDto hpaDto);
}
