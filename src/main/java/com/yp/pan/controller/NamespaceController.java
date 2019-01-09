package com.yp.pan.controller;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.NamespaceDto;
import com.yp.pan.service.ClusterService;
import com.yp.pan.util.ServerException;
import io.fabric8.kubernetes.api.model.DoneableNamespace;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.dsl.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * NamespaceController class
 *
 * @author Administrator
 * @date 2018/11/18
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/namespaces")
public class NamespaceController {
    private final ClusterService clusterService;
    private final static String FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Autowired
    public NamespaceController(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @GetMapping
    public Object namespaces() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        return K8sClient.init(clusterService).namespaces().list().getItems().stream().sorted((x, y) -> {
            try {
                Long dateX = sdf.parse(x.getMetadata().getCreationTimestamp()).getTime();
                Long dateY = sdf.parse(y.getMetadata().getCreationTimestamp()).getTime();
                return dateX.compareTo(dateY);
            } catch (ParseException e) {
                return 1;
            }
        });
    }

    @GetMapping("/user")
    public Object userNamespace(@RequestAttribute String userId,
                                @RequestAttribute String role) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        return K8sClient.init(clusterService)
                .namespaces().list().getItems()
                .stream()
                .filter(namespace -> {
                    if (role.equals(RoleEnum.ADMIN.getRole())) {
                        return true;
                    }
                    String user = namespace.getMetadata().getAnnotations().get(CustomAnno.PAN_USER);
                    if (null != user && user.equals(userId)) {
                        return true;
                    }
                    return false;
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

    @PutMapping
    public Object addNamespace(@RequestBody NamespaceDto namespaceDto, @RequestAttribute String userId) {
        if (namespaceDto == null) {
            throw new ServerException(CustomEnum.NAMESPACE_CREATE_ERROR);
        }
        ObjectMeta namespaceOM = new ObjectMeta();
        namespaceOM.setName(namespaceDto.getName().toLowerCase());
        HashMap<String, String> customMap = new HashMap<>(1);
        customMap.put(CustomAnno.PAN_USER, namespaceDto.getDesc());
        customMap.put(CustomAnno.PAN_DESC, userId);
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

    @DeleteMapping("/{name}")
    public Object deleteNamespace(
            @PathVariable String name,
            @RequestAttribute String userId,
            @RequestAttribute String role) {
        if (StringUtils.isEmpty(name)) {
            throw new ServerException(CustomEnum.NAMESPACE_DELETE_ERROR);
        }
        Resource<Namespace, DoneableNamespace> resource = K8sClient.init(clusterService).namespaces().withName(name);
        //检测到是管理员，强制执行
        if (RoleEnum.ADMIN.getRole().equals(role)) {
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
        if (null == user || !user.equals(userId)) {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        Boolean delete = resource.delete();
        if (delete) {
            return name;
        }
        throw new ServerException(CustomEnum.NAMESPACE_DELETE_ERROR);
    }

//    @PostMapping("/{name}/{newName}")
//    public Object editNamespace(@PathVariable String name, @PathVariable String newName) throws Exception {
//        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(newName)) {
//            throw new ServerException(CustomEnum.NAMESPACE_EDIT_ERROR);
//        }
//        Namespace namespace = new K8sClient().get().namespaces().withName(name).get();
//        namespace.getMetadata().setName(newName);
//        return new K8sClient().get().namespaces().withName(name).createOrReplace(namespace);
//    }
}
