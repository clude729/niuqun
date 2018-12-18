package com.daoyu.niuqun.bean;

/**
 * 朋友圈的图片
 */

public class CircleImage
{

    private static final String TAG = "CircleImage";

    private String image;

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String toString()
    {
        return TAG + ": { url = " + image + " }";
    }

}
