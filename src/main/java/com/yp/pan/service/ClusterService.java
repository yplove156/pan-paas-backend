package com.yp.pan.service;

import com.yp.pan.model.ClusterInfo;

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
}
