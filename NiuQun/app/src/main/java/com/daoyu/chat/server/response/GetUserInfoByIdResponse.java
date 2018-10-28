package com.daoyu.chat.server.response;

import java.util.List;

/**
 * 通过userId查找用户信息的响应
 */
public class GetUserInfoByIdResponse
{

    /**
     * code : 200 result : {"id":"10YVscJI3","nickname":"阿明","portraitUri":""}
     *
     * id : 10YVscJI3 nickname : 阿明 portraitUri :
     */

    private static final String TAG = "GetUserInfoByIdResponse";

    private String code;

    private String message;

    private List<ResultEntity> data;

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

    public void setData(List<ResultEntity> data)
    {
        this.data = data;
    }

    public List<ResultEntity> getData()
    {
        return data;
    }

    public ResultEntity getResult()
    {
        if (null != data && !data.isEmpty())
        {
            return data.get(0);
        }
        return null;
    }

    public static class ResultEntity
    {
        private String user_id;
        private String user_name;
        private String avatar;
        private String token;

        public String getUser_id()
        {
            return user_id;
        }

        public void setUser_id(String user_id)
        {
            this.user_id = user_id;
        }

        public String getUser_name()
        {
            return user_name;
        }

        public void setUser_name(String user_name)
        {
            this.user_name = user_name;
        }

        public String getAvatar()
        {
            return avatar;
        }

        public void setAvatar(String avatar)
        {
            this.avatar = avatar;
        }

        public String getToken()
        {
            return token;
        }

        public void setToken(String token)
        {
            this.token = token;
        }

        public String toString()
        {
            return  "ResultEntity{user_name = " + user_name + "}";
        }
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + " ,data = " + data.toString() + "}";
    }

}
