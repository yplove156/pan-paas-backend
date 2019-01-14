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

    @PostMapping
    public Object addCluster(@RequestBody ClusterInfoDto clusterInfoDto) {
        return clusterService.addCluster(clusterInfoDto);
    }

    @PutMapping
    public Object updateCluster(@RequestBody ClusterInfoDto clusterInfoDto) {
        return clusterService.updateCluster(clusterInfoDto);
    }

    @DeleteMapping("/{id}")
    public Object deleteClusterById(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServerException(CustomEnum.DELETE_CLUSTER_ERROR);
        }
        return clusterService.deleteClusterById(id);
    }

    @GetMapping
    public Object clusterList() {
        return clusterService.clusterList();
    }
}
