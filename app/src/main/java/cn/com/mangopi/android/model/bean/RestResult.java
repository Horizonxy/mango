package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class RestResult<T> implements Serializable {

    private int ret_flag;
    private String error_code;
    private String ret_msg;
    private T data;
    private int total_count;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

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

    public boolean isSuccess(){
        return ret_flag == 1;
    }

    public boolean isFailure(){
        return ret_flag == 0;
    }
}
