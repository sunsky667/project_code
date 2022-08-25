package com.sunsky.dto;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> BaseResponse<T> buildSuccessResult(){
        BaseResponse<T> baseResponse = new BaseResponse<T>();
        baseResponse.setCode(200);
        baseResponse.setMsg("success");
        return baseResponse;
    }
}
