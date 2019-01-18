package com.yp.pan.controller;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.service.ClusterService;
import com.yp.pan.util.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("JavaDoc")
@RestController
@RequestMapping("/pods")
public class PodController {

    private final ClusterService clusterService;

    @Autowired
    public PodController(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    /**
     * 获取命名空间下全部pod实例
     *
     * @param namespace
     * @return
     */
    @GetMapping("/namespace/{namespace}")
    public Object namespacePods(@PathVariable String namespace) {
        return K8sClient.init(clusterService)
                .pods()
                .inNamespace(namespace)
                .list();
    }

    /**
     * 获取deployment应用下全部实例
     *
     * @param namespace
     * @param name
     * @return
     */
    @GetMapping("/deployment/{namespace}/{name}")
    public Object deploymentPods(@PathVariable String namespace, @PathVariable String name) {
        return K8sClient.init(clusterService).pods()
                .inNamespace(namespace)
                .withLabel(CustomAnno.PAN_APP, name)
                .list();
    }

    /**
     * 重启指定实例
     *
     * @param namespace
     * @param name
     * @return
     */
    @DeleteMapping("/reboot/{namespace}/{name}")
    public Object rebootPod(@PathVariable String namespace, @PathVariable String name) {
        boolean delete = K8sClient.init(clusterService)
                .pods()
                .inNamespace(namespace)
                .withName(name)
                .delete();
        if (delete) {
            return true;
        }
        throw new ServerException(CustomEnum.POD_REBOOT_ERROR);
    }

    /**
     * 获取实例详情
     *
     * @param namespace
     * @param name
     * @return
     */
    @GetMapping("/detail/{namespace}/{name}")
    public Object podDetail(@PathVariable String namespace, @PathVariable String name) {
        return K8sClient.init(clusterService)
                .pods()
                .inNamespace(namespace)
                .withName(name)
                .get();
    }

    /**
     * 获取实例完整日志
     *
     * @param namespace
     * @param name
     * @return
     */
    @GetMapping("/log/{namespace}/{name}")
    public Object podLog(@PathVariable String namespace, @PathVariable String name) {
        return K8sClient.init(clusterService)
                .pods()
                .inNamespace(namespace)
                .withName(name)
                .getLog();
    }

    /**
     * 获取实例指定行数日志
     *
     * @param namespace
     * @param name
     * @param size
     * @return
     */
    @GetMapping("/log/{namespace}/{name}/{size}")
    public Object podLog(@PathVariable String namespace,
                         @PathVariable String name,
                         @PathVariable Integer size) {
        return K8sClient.init(clusterService)
                .pods()
                .inNamespace(namespace)
                .withName(name)
                .tailingLines(size)
                .getLog();
    }
}
