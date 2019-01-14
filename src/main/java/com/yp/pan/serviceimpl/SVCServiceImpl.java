package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.SVCService;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SVCServiceImpl implements SVCService {
    private final ClusterService clusterService;

    @Autowired
    public SVCServiceImpl(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public Object services() {
        KubernetesClient client = K8sClient.init(clusterService);
        return client.services().list();
    }

    @Override
    public Object deleteService(String namespace, String name) {
        KubernetesClient client = K8sClient.init(clusterService);
        Boolean delete = false;
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        if (RoleEnum.ADMIN.getRole().equals(userInfo.getRole())) {
            delete = client.configMaps().inNamespace(namespace).withName(name).delete();
        } else {
            ConfigMap configMap = client.configMaps().inNamespace(namespace).withName(name).get();
            String owner = configMap.getMetadata().getAnnotations().get(CustomAnno.PAN_USER);
            if (null != owner && owner.equals(userInfo.getId())) {
                delete = client.configMaps().inNamespace(namespace).withName(name).delete();
            }
        }
        if (delete) {
            return name;
        }
        throw new ServerException(CustomEnum.DELETE_SERVICE_ERROR);
    }
}
