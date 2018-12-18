package com.daoyu.niuqun.bean;

import java.util.List;

/**
 * 朋友圈消息
 */

public class CircleMessage
{

    private static final String TAG = "CircleMessage";

    private String user_id;

    private String content;

    private String add_time;

    private String avatar;

    private String user_name;

    private List<CircleImage> img;

    private List<CircleReply> list;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getAdd_time()
    {
        return add_time;
    }

    public void setAdd_time(String add_time)
    {
        this.add_time = add_time;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public List<CircleImage> getImg()
    {
        return img;
    }

    public void setImg(List<CircleImage> img)
    {
        this.img = img;
    }

    public List<CircleReply> getList()
    {
        return list;
    }

    public void setList(List<CircleReply> list)
    {
        this.list = list;
    }

    public String toString()
    {
        return TAG + ": { user_id = " + user_id + " ,content: " + content + " }";
    }

}
