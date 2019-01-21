package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.ConfigMapDto;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.ConfigMapService;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigMapServiceImpl implements ConfigMapService {
    private final ClusterService clusterService;

    @Autowired
    public ConfigMapServiceImpl(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public Object configMapList() {
        KubernetesClient client = K8sClient.init(clusterService);
        return client.configMaps().list();
    }

    @Override
    public Object createConfigMap(ConfigMapDto configMapDto) {
        KubernetesClient client = K8sClient.init(clusterService);
        ConfigMap configMap = new ConfigMap();

        ObjectMeta meta = new ObjectMeta();
        Map<String, String> annotations = new HashMap<>();
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        annotations.put(CustomAnno.PAN_USER, userInfo.getId());
        meta.setAnnotations(annotations);
        meta.setName(configMapDto.getName());
        configMap.setMetadata(meta);

        configMap.setData(configMapDto.getData());
        return client.configMaps().inNamespace(configMapDto.getNamespace())
                .createOrReplace(configMap);
    }

    @Override
    public String deleteConfigMap(String namespace, String name) {
        KubernetesClient client = K8sClient.init(clusterService);
        Boolean delete = client.configMaps()
                .inNamespace(namespace)
                .withName(name)
                .delete();
        if (delete) {
            return name;
        }
        throw new ServerException(CustomEnum.CONFIG_MAP_DELETE_ERROR);
    }
}
