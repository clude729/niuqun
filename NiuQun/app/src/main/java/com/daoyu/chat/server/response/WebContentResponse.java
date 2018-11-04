package com.daoyu.chat.server.response;

/**
 * webview加载的内容
 */

public class WebContentResponse
{

    private static final String TAG = "WebContentResponse";

    private String code;

    private String message;

    private String data;

    public WebContentResponse()
    {

    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message = " + message + " ,data = " + data + "}";
    }

}
