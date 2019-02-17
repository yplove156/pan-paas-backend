package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.PvDto;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.PvService;
import com.yp.pan.util.ThreadLocalUtil;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * PvServiceImpl class
 *
 * @author Administrator
 * @date 2019/02/14
 */
@Service
public class PvServiceImpl implements PvService {
    private final ClusterService clusterService;

    @Autowired
    public PvServiceImpl(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public Object getAllPv() {
        KubernetesClient client = K8sClient.init(clusterService);
        return null;
    }

    @Override
    public Object createPv(PvDto pvDto) {
        KubernetesClient client = K8sClient.init(clusterService);
        PersistentVolume pv = new PersistentVolume();
        ObjectMeta meta = new ObjectMeta();
        meta.setName(pvDto.getName());
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        Map<String, String> anno = new HashMap<>(2);
        anno.put(CustomAnno.PAN_USER, userInfo.getId());
        meta.setAnnotations(anno);
        pv.setMetadata(meta);

        PersistentVolumeSpec spec = new PersistentVolumeSpec();
        spec.setAccessModes(pvDto.getAccessModes());
        Map<String, Quantity> capacity = new HashMap<>();
        Quantity quantity = new Quantity();
        quantity.setAmount("1");
        quantity.setFormat("Gi");
        capacity.put("storage",quantity);
        spec.setCapacity(capacity);
        spec.setPersistentVolumeReclaimPolicy(pvDto.getPersistentVolumeReclaimPolicy());
        NFSVolumeSource nfs = new NFSVolumeSource();
        nfs.setServer(pvDto.getNfsServer());
        nfs.setPath(pvDto.getNfsPath());
        spec.setNfs(nfs);
        pv.setSpec(spec);
        return client.persistentVolumes().withName("")
                .createOrReplace(pv);
    }
}
