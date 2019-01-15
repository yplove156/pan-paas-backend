package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.LogCode;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.NamespaceDto;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.NamespaceService;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import io.fabric8.kubernetes.api.model.DoneableNamespace;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.dsl.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
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

    private final NamespaceService namespaceService;

    @Autowired
    public NamespaceController(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @GetMapping
    public Object namespaces() {
        return namespaceService.namespaces();

    }

    @GetMapping("/user")
    public Object userNamespace() {
        return namespaceService.userNamespace();
    }

    @PostMapping
    @PanLog(LogCode.ADD_NAMESPACE_LOG)
    public Object addNamespace(@RequestBody NamespaceDto namespaceDto) {
        if (namespaceDto == null) {
            throw new ServerException(CustomEnum.NAMESPACE_CREATE_ERROR);
        }
        return namespaceService.addNamespace(namespaceDto);
    }

    @DeleteMapping("/{name}")
    @PanLog(LogCode.DELETE_NAMESPACE_LOG)
    public Object deleteNamespace(@PathVariable String name) {
        if (StringUtils.isEmpty(name)) {
            throw new ServerException(CustomEnum.NAMESPACE_DELETE_ERROR);
        }
        return namespaceService.deleteNamespace(name);
    }

}
