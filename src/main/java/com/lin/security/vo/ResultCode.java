package com.lin.security.vo;


public enum ResultCode implements StatusCode{

    SUCCESS(1000,"请求成功"),
    FAILED(1001,"请求失败"),
    VALIDATE_ERROR(1002,"参数校验失败"),
    RESPONSE_PACK_ERROR(1003,"response返回包装失败"),
    ERROR(500,"系统错误，请联系管理"),
    AUTHENTICATION_ERROR(403,"授权失败");
    private int code;
    private String msg;

    ResultCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
