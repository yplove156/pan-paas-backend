package com.yp.pan.config;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.model.ClusterInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.util.ServerException;
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
public class K8sClient {

    public static KubernetesClient init(ClusterService clusterService) {
        ClusterInfo clusterInfo;
        try {
            clusterInfo = clusterService.getCluster();
        } catch (Exception e) {
            throw new ServerException(CustomEnum.INIT_CLUSTER_ERROR);
        }
        Config config = new ConfigBuilder().withMasterUrl(clusterInfo.getUrl())
                .withCaCertData(clusterInfo.getCaCertData())
                .withClientCertData(clusterInfo.getClientCertData())
                .withClientKeyData(clusterInfo.getClientKeyData())
                .build();
        return new DefaultKubernetesClient(config);
    }
}
