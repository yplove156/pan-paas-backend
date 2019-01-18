package com.yp.pan.controller;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.NodeLabelDto;
import com.yp.pan.service.ClusterService;
import com.yp.pan.util.ServerException;
import io.fabric8.kubernetes.api.model.DoneableNode;
import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Object getNodes() {
        return K8sClient.init(clusterService).nodes().list().getItems();
    }

    @GetMapping("/{name}")
    public Object getDetail(@PathVariable String name) {
        if (StringUtils.isEmpty(name)) {
            throw new ServerException(CustomEnum.NODE_DETAIL_ERROR);
        }
        return K8sClient.init(clusterService).nodes().withName(name).get();
    }

    @PostMapping("/labels")
    public Object addLabels(@RequestBody NodeLabelDto nodeLabelDto) {
        return K8sClient.init(clusterService)
                .nodes()
                .withName(nodeLabelDto.getName())
                .edit()
                .editMetadata()
                .addToLabels(nodeLabelDto.getLabels())
                .endMetadata()
                .done();
    }
}
