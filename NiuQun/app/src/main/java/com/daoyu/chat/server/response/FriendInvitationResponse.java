package com.daoyu.chat.server.response;

/**
 * 发送好友邀请响应类
 */
public class FriendInvitationResponse
{

    /**
     * code : 200 result : {"action":"Sent"} message : Request sent.
     * action : Sent
     */

    private static final String TAG = "FriendInvitationResponse";

    private String code;

    private String message;

    private ResultEntity data;

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

    public ResultEntity getData() {
        return data;
    }

    public void setData(ResultEntity data) {
        this.data = data;
    }

    public void setResult(ResultEntity result)
    {
        this.data = result;
    }

    public ResultEntity getResult()
    {
        return data;
    }

    public static class ResultEntity
    {
        private String action;

        public void setAction(String action)
        {
            this.action = action;
        }

        public String getAction()
        {
            return action;
        }

        public String toString()
        {
            return  "ResultEntity{action = " + action + "}";
        }

    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + " ,data = " + data + "}";
    }

}
