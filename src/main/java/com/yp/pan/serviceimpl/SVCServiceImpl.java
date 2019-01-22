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
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
            delete = client.services().inNamespace(namespace).withName(name).delete();
        } else {
            io.fabric8.kubernetes.api.model.Service service = client.services().inNamespace(namespace).withName(name).get();
            String owner = service.getMetadata().getAnnotations().get(CustomAnno.PAN_USER);
            if (null != owner && owner.equals(userInfo.getId())) {
                delete = client.services().inNamespace(namespace).withName(name).delete();
            }
        }
        if (delete) {
            return name;
        }
        throw new ServerException(CustomEnum.DELETE_SERVICE_ERROR);
    }

    @Override
    public Object addService() {
        KubernetesClient client = K8sClient.init(clusterService);
        io.fabric8.kubernetes.api.model.Service service = new io.fabric8.kubernetes.api.model.Service();
        ObjectMeta meta = new ObjectMeta();
        Map<String, String> anno = new HashMap<>();
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        anno.put(CustomAnno.PAN_USER, userInfo.getId());
        anno.put(CustomAnno.PAN_DESC, userInfo.getId());
        meta.setAnnotations(anno);
        service.setMetadata(meta);
        io.fabric8.kubernetes.api.model.Service replace = client.services().inNamespace("")
                .withName("")
                .createOrReplace();
        return null;
    }
}
