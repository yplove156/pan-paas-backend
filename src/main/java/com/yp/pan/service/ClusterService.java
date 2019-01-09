package com.yp.pan.service;

import com.yp.pan.dto.ClusterInfoDto;
import com.yp.pan.model.ClusterInfo;

import java.util.List;

/**
 * ClusterService class
 *
 * @author Administrator
 * @date 2018/12/04
 */
public interface ClusterService {

    ClusterInfo getCluster();

    int addCluster(ClusterInfoDto clusterInfoDto);

    int deleteClusterById(String id);

    List<ClusterInfoDto> clusterList();

    int updateCluster(ClusterInfoDto clusterInfoDto);
}
