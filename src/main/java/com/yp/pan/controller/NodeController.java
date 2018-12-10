package com.yp.pan.controller;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.service.ClusterService;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NodeController class
 *
 * @author Administrator
 * @date 2018/11/19
 */
@RestController
@RequestMapping("/nodes")
public class NodeController {

    private final ClusterService clusterService;

    @Autowired
    public NodeController(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @GetMapping
    public Object getNodes() throws Exception {
        return new K8sClient(clusterService).get().nodes().list().getItems();
    }

    @GetMapping("/{name}")
    public Object getDetail(@PathVariable String name) throws Exception {
        if (StringUtils.isEmpty(name)) {
            throw new ServerException(CustomEnum.NODE_DETAIL_ERROR);
        }
        return new K8sClient(clusterService).get().nodes().withName(name).get();
    }
}
