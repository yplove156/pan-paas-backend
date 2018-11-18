package com.yp.pan.controller;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.dto.NamespaceDto;
import com.yp.pan.util.ServerException;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

/**
 * NamespaceController class
 *
 * @author Administrator
 * @date 2018/11/18
 */
@RestController
@RequestMapping("/namespaces")
public class NamespaceController {

    private final KubernetesClient client;

    @Autowired
    public NamespaceController(KubernetesClient client) {
        this.client = client;
    }

    @GetMapping
    public Object namespaces() throws Exception {
        return client.namespaces().list().getItems();
    }

    @PutMapping
    public Object addNamespace(@RequestBody NamespaceDto namespaceDto) throws Exception {
        if (namespaceDto == null) {
            throw new ServerException(CustomEnum.NAMESPACE_CREATE_ERROR);
        }
        ObjectMeta namespaceOM = new ObjectMeta();
        namespaceOM.setName(namespaceDto.getName().toLowerCase());
        Namespace namespace = new Namespace();
        namespace.setMetadata(namespaceOM);
        Namespace replaceOrNew;
        try {
            replaceOrNew = client.namespaces().createOrReplace(namespace);
            if (replaceOrNew != null) {
                return replaceOrNew;
            }
            throw new ServerException(CustomEnum.NAMESPACE_CREATE_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(CustomEnum.NAMESPACE_CREATE_ERROR);
        }
    }

    @DeleteMapping("/{name}")
    public Object deleteNamespace(@PathVariable String name) throws Exception {
        if (StringUtils.isEmpty(name)) {
            throw new ServerException(CustomEnum.NAMESPACE_DELETE_ERROR);
        }
        Boolean delete = client.namespaces().withName(name).delete();
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
//        Namespace namespace = client.namespaces().withName(name).get();
//        namespace.getMetadata().setName(newName);
//        return client.namespaces().withName(name).createOrReplace(namespace);
//    }
}
