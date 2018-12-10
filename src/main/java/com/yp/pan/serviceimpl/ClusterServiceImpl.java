package com.yp.pan.serviceimpl;

import com.yp.pan.mapper.ClusterMapper;
import com.yp.pan.model.ClusterInfo;
import com.yp.pan.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClusterServiceImpl class
 *
 * @author Administrator
 * @date 2018/12/04
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ClusterServiceImpl implements ClusterService {
    private final ClusterMapper clusterMapper;

    @Autowired
    public ClusterServiceImpl(ClusterMapper clusterMapper) {
        this.clusterMapper = clusterMapper;
    }

    @Override
    public ClusterInfo getCluster() throws Exception {
        return clusterMapper.getCluster();
    }

    @Override
    public int addCluster(ClusterInfo clusterInfo) throws Exception {
        return clusterMapper.addCluster(clusterInfo);
    }

    @Override
    public int deleteClusterById(String id) {
        return clusterMapper.deleteClusterById(id);
    }
}
