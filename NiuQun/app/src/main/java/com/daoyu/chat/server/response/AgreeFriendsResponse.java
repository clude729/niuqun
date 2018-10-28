package com.daoyu.chat.server.response;

/**
 * 同意添加好友响应类
 */
public class AgreeFriendsResponse
{

    private static final String TAG = "AgreeFriendsResponse";

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
