package com.daoyu.chat.server.request;

/**
 * Created by AMing on 16/1/13. Company RongCloud
 */
public class SetPortraitRequest
{

    private String avatar;

    private String user_id;

    public SetPortraitRequest(String avatar, String user_id)
    {
        this.avatar = avatar;
        this.user_id = user_id;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }
}
