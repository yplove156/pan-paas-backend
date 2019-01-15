package com.yp.pan.serviceimpl;

import com.yp.pan.config.K8sClient;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.YamlService;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class YamlServiceImpl implements YamlService {

    private final ClusterService clusterService;

    @Autowired
    public YamlServiceImpl(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public Object deployYaml(InputStream inputStream) {
        KubernetesClient client = K8sClient.init(clusterService);
        client.load(inputStream).createOrReplace();
        return null;
    }
}
