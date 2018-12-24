package com.yp.pan.serviceimpl;

import com.yp.pan.dto.ClusterInfoDto;
import com.yp.pan.mapper.ClusterMapper;
import com.yp.pan.model.ClusterInfo;
import com.yp.pan.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ClusterInfo getCluster() {
        return clusterMapper.getCluster();
    }

    @Override
    public int addCluster(ClusterInfo clusterInfo) {
        if (clusterInfo.getOpen() == 1) {
            clusterMapper.closeOpen();
        }
        return clusterMapper.addCluster(clusterInfo);
    }

    @Override
    public int deleteClusterById(String id) {
        return clusterMapper.deleteClusterById(id);
    }

    @Override
    public List<ClusterInfo> clusterList() {
        return clusterMapper.clusterList();
    }

    @Override
    public int updateCluster(ClusterInfoDto clusterInfoDto) {
        return clusterMapper.updateCluster(clusterInfoDto);
    }
}
