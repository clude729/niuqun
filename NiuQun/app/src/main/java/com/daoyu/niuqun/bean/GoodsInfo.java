package com.daoyu.niuqun.bean;

/**
 * 品牌、新品
 */

public class GoodsInfo
{

    private static final String TAG = "GoodsInfo";

    private String goods_id;

    private String cate_id;

    private String goods_name;

    private String goods_price;

    private String intro;

    private String adv_word;

    private String add_time;

    private String is_del;

    private String is_check;

    private String isadv;

    private String thumb_image;

    public GoodsInfo()
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

    public String getCate_id()
    {
        return cate_id;
    }

    public void setCate_id(String cate_id)
    {
        this.cate_id = cate_id;
    }

    public String getGoods_name()
    {
        return goods_name;
    }

    public void setGoods_name(String goods_name)
    {
        this.goods_name = goods_name;
    }

    public String getGoods_price()
    {
        return goods_price;
    }

    public void setGoods_price(String goods_price)
    {
        this.goods_price = goods_price;
    }

    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public String getAdv_word()
    {
        return adv_word;
    }

    public void setAdv_word(String adv_word)
    {
        this.adv_word = adv_word;
    }

    public String getAdd_time()
    {
        return add_time;
    }

    public void setAdd_time(String add_time)
    {
        this.add_time = add_time;
    }

    public String getIs_del()
    {
        return is_del;
    }

    public void setIs_del(String is_del)
    {
        this.is_del = is_del;
    }

    public String getIs_check()
    {
        return is_check;
    }

    public void setIs_check(String is_check)
    {
        this.is_check = is_check;
    }

    public String getIsadv()
    {
        return isadv;
    }

    public void setIsadv(String isadv)
    {
        this.isadv = isadv;
    }

    public String toString()
    {
        return TAG + ": { goods = " + goods_id + " }";
    }

}
