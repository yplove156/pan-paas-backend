package com.yp.pan.controller;

import com.yp.pan.config.K8sClient;
import com.yp.pan.util.UUIDUtil;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.soap.Name;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
public class TestController {

    @Autowired
    KubernetesClient client;

    @RequestMapping("/test")
    public Object test() {
        ObjectMeta meta = new ObjectMeta();
        HashMap<String, String> map = new HashMap<>(1);
        map.put("desc", "而是热热身他");
        meta.setAnnotations(map);
        meta.setName("niubi");
        Namespace namespace = new Namespace();
        namespace.setMetadata(meta);
        Namespace orReplace = client.namespaces().createOrReplace(namespace);
        return orReplace;
    }
}
