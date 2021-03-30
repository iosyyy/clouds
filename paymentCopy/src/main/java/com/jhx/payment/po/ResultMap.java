package com.jhx.payment.po;

import java.io.Serializable;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
public class ResultMap<T> implements Serializable {
    private Integer code;
    private String message;
    private T      types;

    public ResultMap(Integer code, String message, T types) {
        this.code = code;
        this.message = message;
        this.types = types;
    }

    public ResultMap() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getTypes() {
        return types;
    }

    public void setTypes(T types) {
        this.types = types;
    }

    public ResultMap(Integer code, String message) {
        this.code=code;
        this.message=message;
        this.types=null;
    }
}
