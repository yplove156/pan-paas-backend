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

    ClusterInfo getCluster() throws Exception;

    int addCluster(ClusterInfo clusterInfo) throws Exception;

    int deleteClusterById(String id);

    List<ClusterInfo> clusterList();

    int updateCluster(ClusterInfoDto clusterInfoDto);
}
