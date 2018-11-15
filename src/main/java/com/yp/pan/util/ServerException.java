package com.yp.pan.util;

import com.yp.pan.common.CustomEnum;

/**
 * ServerException class
 *
 * @author Administrator
 * @date 2018/11/15
 */
public class ServerException extends RuntimeException {

    private String code;

    public ServerException(CustomEnum customEnum) {
        super(customEnum.getMsg());
        this.code = customEnum.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
