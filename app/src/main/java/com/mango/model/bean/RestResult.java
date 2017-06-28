package com.mango.model.bean;

import java.io.Serializable;

public class RestResult<T> implements Serializable {

    private int ret_flag;
    private String error_code;
    private String ret_msg;
    private T data;

    public int getRet_flag() {
        return ret_flag;
    }

    public void setRet_flag(int ret_flag) {
        this.ret_flag = ret_flag;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
