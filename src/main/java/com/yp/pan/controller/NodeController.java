package com.yp.pan.controller;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.util.ServerException;
import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.client.KubernetesClient;
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
    private final KubernetesClient client;

    @Autowired
    public NodeController(KubernetesClient client) {
        this.client = client;
    }

    @GetMapping
    public Object getNodes() throws Exception {
        return client.nodes().list().getItems();
    }

    @GetMapping("/{name}")
    public Object getDetail(@PathVariable String name) throws Exception {
        if (StringUtils.isEmpty(name)) {
            throw new ServerException(CustomEnum.NODE_DETAIL_ERROR);
        }
        return client.nodes().withName(name).get();
    }
}
