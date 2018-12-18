package com.daoyu.niuqun.bean;

import java.io.File;

/**
 * 新朋友圈消息
 */

public class CircleNewMessage
{

    private static final String TAG = "CircleNewMessage";

    private String content;

    private File img;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public File getImg()
    {
        return img;
    }

    public void setImg(File img)
    {
        this.img = img;
    }

    public String toString()
    {
        return TAG + ": { content = " + content + " }";
    }

}
