package com.yp.pan.controller;

import com.yp.pan.annotation.PanLog;
import com.yp.pan.common.LogCode;
import com.yp.pan.dto.ParamsDto;
import com.yp.pan.dto.SecretDto;
import com.yp.pan.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * SecretController class
 *
 * @author Administrator
 * @date 2019/02/14
 */
@RestController
@RequestMapping("/secrets")
public class SecretController {

    private final SecretService secretService;

    @Autowired
    public SecretController(SecretService secretService) {
        this.secretService = secretService;
    }

    @GetMapping("/namespaces/{namespace}")
    public Object getSecretInNamespace(@PathVariable String namespace) {
        return secretService.getSecretInNamespace(namespace);
    }

    @GetMapping("/namespaces/{namespace}/{secret:.+}")
    public Object getSecretDetail(@PathVariable String namespace, @PathVariable String secret) {
        return secretService.getSecretDetail(namespace, secret);
    }

    @PostMapping
    public Object createSecret(@RequestBody SecretDto secretDto) {
        return secretService.createSecret(secretDto);
    }

    @DeleteMapping("/namespaces/{namespace}/{name}")
    @PanLog(LogCode.DELETE_SECRET_LOG)
    public Object deleteSecret(@PathVariable String namespace,
                               @PathVariable String name) {
        return secretService.deleteSecret(namespace, name);
    }
}
