package com.yp.pan.controller;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.ClusterInfoDto;
import com.yp.pan.model.ClusterInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * ClusterController class
 *
 * @author Administrator
 * @date 2018/12/05
 */
@RestController
@RequestMapping("/cluster")
public class ClusterController {
    private final ClusterService clusterService;

    @Autowired
    public ClusterController(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @PutMapping
    public Object addCluster(@RequestBody ClusterInfoDto clusterInfoDto) throws Exception {
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
        int res = clusterService.addCluster(clusterInfo);
        if (res == 1) {
            return null;
        }
        throw new ServerException(CustomEnum.ADD_CLUSTER_ERROR);
    }

    @DeleteMapping("/{id}")
    public Object deleteClusterById(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServerException(CustomEnum.COMMON_EXCEPTION);
        }
        int res = clusterService.deleteClusterById(id);
        if (res == 1) {
            return id;
        }
        throw new ServerException(CustomEnum.COMMON_EXCEPTION);
    }
}
