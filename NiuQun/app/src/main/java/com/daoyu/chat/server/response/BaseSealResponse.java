package com.daoyu.chat.server.response;

/**
 * Seal网络框架响应基类
 */
public class BaseSealResponse
{

    private static final String TAG = "BaseSealResponse";

    private String code;

    private String message;

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

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + "}";
    }

}
