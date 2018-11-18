package com.yp.pan.config;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * K8sClient class
 *
 * @author Administrator
 * @date 2018/11/18
 */
@Configuration
public class K8sClient {
    @Value("${k8s.url}")
    private String url;

    @Bean
    public KubernetesClient kubernetesClient() {
        Config config = new ConfigBuilder().withMasterUrl(url).build();
        return new DefaultKubernetesClient(config);
    }
}
