package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.ConfigMapDto;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.ConfigMapService;
import com.yp.pan.util.RoleUtil;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Service
public class ConfigMapServiceImpl implements ConfigMapService {
    private final ClusterService clusterService;

    @Autowired
    public ConfigMapServiceImpl(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public Object configMapList() {
        SimpleDateFormat sdf = new SimpleDateFormat(CustomAnno.K8S_TIME_FORMAT);
        KubernetesClient client = K8sClient.init(clusterService);
        List<ConfigMap> configMaps = client.configMaps().list().getItems();
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        return configMaps.stream()
                .filter(namespace -> {
                    if (RoleUtil.isAdmin(userInfo.getRole())) {
                        return true;
                    }
                    return RoleUtil.isOwner(namespace.getMetadata(), userInfo.getId());
                })
                .sorted((x, y) -> {
                    try {
                        Long dateX = sdf.parse(x.getMetadata().getCreationTimestamp()).getTime();
                        Long dateY = sdf.parse(y.getMetadata().getCreationTimestamp()).getTime();
                        return dateX.compareTo(dateY);
                    } catch (ParseException e) {
                        return 1;
                    }
                });
    }

    @Override
    public Object createConfigMap(ConfigMapDto configMapDto) {
        KubernetesClient client = K8sClient.init(clusterService);
        ConfigMap configMap = new ConfigMap();

        ObjectMeta meta = new ObjectMeta();
        Map<String, String> annotations = new HashMap<>();
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        annotations.put(CustomAnno.PAN_USER, userInfo.getId());
        annotations.put(CustomAnno.PAN_DESC, userInfo.getId());
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
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        ConfigMap configMap = client.configMaps()
                .inNamespace(namespace)
                .withName(name)
                .get();
        boolean res;
        if (RoleUtil.isAdmin(userInfo.getRole())) {
            res = delete(client, namespace, name);
        } else if (RoleUtil.isOwner(configMap.getMetadata(), userInfo.getId())) {
            res = delete(client, namespace, name);
        } else {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        if (res) {
            return name;
        }
        throw new ServerException(CustomEnum.CONFIG_MAP_DELETE_ERROR);
    }

    private boolean delete(KubernetesClient client, String namespace, String name) {
        return client.configMaps()
                .inNamespace(namespace)
                .withName(name)
                .delete();
    }
}
