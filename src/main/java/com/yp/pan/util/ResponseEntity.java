package com.yp.pan.util;

/**
 * ResponseEntity class
 *
 * @author Administrator
 * @date 2018/11/15
 */
public class ResponseEntity<T> {
    private Boolean status;
    private String code;
    private String message;
    private T data;

    private ResponseEntity(Boolean status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity getFail(String code, String message) {
        return new ResponseEntity<>(false, code, message, null);
    }

    public static <T> ResponseEntity getSuccess(T data) {
        return new ResponseEntity<>(true, null, null, data);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
