package com.yp.pan.controller;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.ClusterInfoDto;
import com.yp.pan.model.ClusterInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ClusterController class
 *
 * @author Administrator
 * @date 2018/12/05
 */
@RestController
@RequestMapping("/clusters")
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

    @PostMapping
    public Object updateCluster(@RequestBody ClusterInfoDto clusterInfoDto) throws Exception {
        clusterInfoDto.setCreateTime(System.currentTimeMillis());
        int res = clusterService.updateCluster(clusterInfoDto);
        if (res == 1) {
            return null;
        }
        throw new ServerException(CustomEnum.EDIT_CLUSTER_ERROR);
    }

    @DeleteMapping("/{id}")
    public Object deleteClusterById(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServerException(CustomEnum.DELETE_CLUSTER_ERROR);
        }
        int res = clusterService.deleteClusterById(id);
        if (res == 1) {
            return id;
        }
        throw new ServerException(CustomEnum.DELETE_CLUSTER_ERROR);
    }

    @GetMapping
    public Object clusterList() throws Exception {
        List<ClusterInfoDto> clusterInfoDtos = new ArrayList<>();
        List<ClusterInfo> clusterInfos = clusterService.clusterList();
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
}
