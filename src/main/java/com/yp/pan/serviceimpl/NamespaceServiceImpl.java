package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.NamespaceDto;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.NamespaceService;
import com.yp.pan.util.RoleUtil;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import io.fabric8.kubernetes.api.model.DoneableNamespace;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.dsl.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Service
public class NamespaceServiceImpl implements NamespaceService {

    private final static String FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private final ClusterService clusterService;

    @Autowired
    public NamespaceServiceImpl(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public Object namespaces() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        List<Namespace> namespaces = K8sClient.init(clusterService).namespaces().list().getItems();
        return namespaces.stream().sorted((x, y) -> {
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
    public Object userNamespace() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        List<Namespace> namespaces = K8sClient.init(clusterService).namespaces().list().getItems();
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        return namespaces.stream()
                .filter(namespace -> {
                    if (RoleUtil.isAdmin(userInfo.getRole())) {
                        return true;
                    }
                    String user = namespace.getMetadata().getAnnotations().get(CustomAnno.PAN_USER);
                    return null != user && user.equals(userInfo.getId());
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
    public Object addNamespace(NamespaceDto namespaceDto) {
        ObjectMeta namespaceOM = new ObjectMeta();
        namespaceOM.setName(namespaceDto.getName().toLowerCase());
        HashMap<String, String> customMap = new HashMap<>();
        customMap.put(CustomAnno.PAN_DESC, namespaceDto.getDesc());
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        customMap.put(CustomAnno.PAN_USER, userInfo.getId());
        namespaceOM.setAnnotations(customMap);
        Namespace namespace = new Namespace();
        namespace.setMetadata(namespaceOM);
        Namespace replaceOrNew;
        try {
            replaceOrNew = K8sClient.init(clusterService).namespaces().createOrReplace(namespace);
            if (replaceOrNew != null) {
                return replaceOrNew;
            }
            throw new ServerException(CustomEnum.NAMESPACE_CREATE_ERROR);
        } catch (Exception e) {
            throw new ServerException(CustomEnum.NAMESPACE_CREATE_ERROR);
        }
    }

    @Override
    public Object deleteNamespace(String name) {
        Resource<Namespace, DoneableNamespace> resource = K8sClient.init(clusterService).namespaces().withName(name);
        //检测到是管理员，强制执行
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        if (RoleEnum.ADMIN.getRole().equals(userInfo.getRole())) {
            Boolean delete = resource.delete();
            if (delete) {
                return name;
            }
            throw new ServerException(CustomEnum.NAMESPACE_DELETE_ERROR);
        }
        //否则验证用户是否有权限
        Namespace namespace = resource.get();
        Map<String, String> annotations = namespace.getMetadata().getAnnotations();
        String user = annotations.get(CustomAnno.PAN_USER);
        if (null == user || !user.equals(userInfo.getId())) {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        Boolean delete = resource.delete();
        if (delete) {
            return name;
        }
        throw new ServerException(CustomEnum.NAMESPACE_DELETE_ERROR);
    }
}
