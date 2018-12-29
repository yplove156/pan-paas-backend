package com.yp.pan.config;

import com.yp.pan.model.ClusterInfo;
import com.yp.pan.service.ClusterService;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * K8sClient class
 *
 * @author Administrator
 * @date 2018/11/18
 */
@Component
public class K8sClient {

    private final ClusterService clusterService;

    @Autowired
    public K8sClient(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    public KubernetesClient get() {
        ClusterInfo clusterInfo;
        try {
            clusterInfo = clusterService.getCluster();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Config config = new ConfigBuilder().withMasterUrl(clusterInfo.getUrl())
                .withCaCertData(clusterInfo.getCaCertData())
                .withClientCertData(clusterInfo.getClientCertData())
                .withClientKeyData(clusterInfo.getClientKeyData())
                .build();
        return new DefaultKubernetesClient(config);
    }
}
