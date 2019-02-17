package com.yp.pan.serviceimpl;

import com.yp.pan.common.CommonData;
import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.ParamsDto;
import com.yp.pan.dto.SecretDto;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.SecretService;
import com.yp.pan.util.RoleUtil;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SecretServiceImpl class
 *
 * @author Administrator
 * @date 2019/02/14
 */
@SuppressWarnings({"SpellCheckingInspection", "Duplicates"})
@Service
public class SecretServiceImpl implements SecretService {

    private final ClusterService clusterService;

    @Autowired
    public SecretServiceImpl(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public Object createSecret(SecretDto secretDto) {
        KubernetesClient client = K8sClient.init(clusterService);
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        Secret secret = new Secret();
        ObjectMeta meta = new ObjectMeta();
        meta.setName(secretDto.getName());
        Map<String, String> anno = new HashMap<>(2);
        anno.put(CustomAnno.PAN_USER, userInfo.getId());
        meta.setAnnotations(anno);
        secret.setMetadata(meta);
        secret.setType("Opaque");
        secret.setData(secretDto.getData());
        return client.secrets().inNamespace(secretDto.getNamespace())
                .withName(secretDto.getName())
                .createOrReplace(secret);
    }

    @Override
    public String deleteSecret(String namespace, String name) {
        KubernetesClient client = K8sClient.init(clusterService);
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        Secret secret = client.secrets()
                .inNamespace(namespace)
                .withName(name)
                .get();
        boolean res;
        if (RoleUtil.isAdmin(userInfo.getRole())) {
            res = delete(client, namespace, name);
        } else if (RoleUtil.isOwner(secret.getMetadata(), userInfo.getId())) {
            res = delete(client, namespace, name);
        } else {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        if (res) {
            return name;
        }
        throw new ServerException(CustomEnum.SECRET_DELETE_ERROR);
    }

    @Override
    public Object getSecretInNamespace(String namespace) {
        KubernetesClient client = K8sClient.init(clusterService);
        return client.secrets().inNamespace(namespace).list().getItems();
    }

    @Override
    public Object getSecretDetail(String namespace, String secret) {
        KubernetesClient client = K8sClient.init(clusterService);
        return client.secrets().inNamespace(namespace).withName(secret).get();
    }

    private boolean delete(KubernetesClient client, String namespace, String name) {
        return client.secrets()
                .inNamespace(namespace)
                .withName(name)
                .delete();
    }
}
