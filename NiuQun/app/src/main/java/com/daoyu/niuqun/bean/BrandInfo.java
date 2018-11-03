package com.daoyu.niuqun.bean;

/**
 * 品牌、新品
 */

public class BrandInfo
{

    private static final String TAG = "BrandInfo";

    private String goods_id;

    private String thumb_image;

    public BrandInfo()
    {

    }

    public String getGoods_id()
    {
        return goods_id;
    }

    public void setGoods_id(String goods_id)
    {
        this.goods_id = goods_id;
    }

    public String getThumb_image()
    {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image)
    {
        this.thumb_image = thumb_image;
    }

    public String toString()
    {
        return TAG + ": { goods = " + goods_id + " }";
    }

}
