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

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return client.namespaces().list().getItems().stream().sorted((x, y) -> {
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
    public Object addNamespace(@RequestBody NamespaceDto namespaceDto) throws Exception {
        if (namespaceDto == null) {
            throw new ServerException(CustomEnum.NAMESPACE_CREATE_ERROR);
        }
        ObjectMeta namespaceOM = new ObjectMeta();
        namespaceOM.setName(namespaceDto.getName().toLowerCase());
        HashMap<String,String> customMap = new HashMap<>(1);
        customMap.put("desc",namespaceDto.getDesc());
        namespaceOM.setAnnotations(customMap);
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
