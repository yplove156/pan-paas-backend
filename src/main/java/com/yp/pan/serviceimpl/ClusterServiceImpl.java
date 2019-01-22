package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.ClusterInfoDto;
import com.yp.pan.mapper.ClusterMapper;
import com.yp.pan.model.ClusterInfo;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        return clusterMapper.getCluster(userInfo.getId());
    }

    @Override
    public int addCluster(ClusterInfoDto clusterInfoDto) {
        ClusterInfo clusterInfo = new ClusterInfo();
        clusterInfo.setId(UUID.randomUUID().toString());
        clusterInfo.setCaCertData(clusterInfoDto.getCaCertData());
        clusterInfo.setClientCertData(clusterInfoDto.getClientCertData());
        clusterInfo.setClientKeyData(clusterInfoDto.getClientKeyData());
        clusterInfo.setOpen(clusterInfoDto.getOpen());
        clusterInfo.setUrl(clusterInfoDto.getUrl());
        clusterInfo.setCreateTime(System.currentTimeMillis());
        clusterInfo.setUpdateTime(System.currentTimeMillis());
        clusterInfo.setDeleteFlag(0);
        if (clusterInfo.getOpen() == 1) {
            clusterMapper.closeOpen();
        }
        int res = clusterMapper.addCluster(clusterInfo);
        if (res == 1) {
            return res;
        }
        throw new ServerException(CustomEnum.ADD_CLUSTER_ERROR);
    }

    @Override
    public int deleteClusterById(String id) {
        int res = clusterMapper.deleteClusterById(id);
        if (res == 1) {
            return res;
        }
        throw new ServerException(CustomEnum.DELETE_CLUSTER_ERROR);
    }

    @Override
    public List<ClusterInfoDto> clusterList() {
        List<ClusterInfoDto> clusterInfoDtos = new ArrayList<>();
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        List<ClusterInfo> clusterInfos = clusterMapper.clusterList(userInfo.getId());
        clusterInfos.forEach(clusterInfo -> {
            ClusterInfoDto clusterInfoDto = new ClusterInfoDto();
            clusterInfoDto.setId(clusterInfo.getId());
            clusterInfoDto.setCaCertData(clusterInfo.getCaCertData());
            clusterInfoDto.setClientCertData(clusterInfo.getClientCertData());
            clusterInfoDto.setClientKeyData(clusterInfo.getClientKeyData());
            clusterInfoDto.setOpen(clusterInfo.getOpen());
            clusterInfoDto.setUrl(clusterInfo.getUrl());
            clusterInfoDto.setCreateTime(clusterInfo.getCreateTime());
            clusterInfoDtos.add(clusterInfoDto);
        });
        return clusterInfoDtos;
    }

    @Override
    public int updateCluster(ClusterInfoDto clusterInfoDto) {
        clusterInfoDto.setCreateTime(System.currentTimeMillis());
        int res = clusterMapper.updateCluster(clusterInfoDto);
        if (res == 1) {
            return res;
        }
        throw new ServerException(CustomEnum.EDIT_CLUSTER_ERROR);
    }
}
