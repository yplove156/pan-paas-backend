package com.yp.pan.service;

import com.yp.pan.dto.ParamsDto;
import com.yp.pan.dto.SecretDto;

/**
 * SecretService class
 *
 * @author Administrator
 * @date 2019/02/14
 */
public interface SecretService {

    Object createSecret(SecretDto secretDto);

    String deleteSecret(String namespace, String name);

    Object getSecretInNamespace(String namespace);

    Object getSecretDetail(String namespace, String secret);
}
