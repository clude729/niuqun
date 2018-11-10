package com.daoyu.niuqun.bean;

/**
 * 购物车内商品
 */

public class CartGoodsInfo
{

    private static final String TAG = "CartGoodsInfo";

    private String cart_id;

    private String goods_id;

    private String goods_name;

    private String goods_price;

    private String quantity;

    private String status;

    private String user_id;

    private String thumb_image;

    private boolean hasCheck = false;

    public CartGoodsInfo()
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

    public String getCart_id()
    {
        return cart_id;
    }

    public void setCart_id(String cart_id)
    {
        this.cart_id = cart_id;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public boolean isHasCheck()
    {
        return hasCheck;
    }

    public void setHasCheck(boolean hasCheck)
    {
        this.hasCheck = hasCheck;
    }

    public String toString()
    {
        return TAG + ": { goods = " + goods_id + " }";
    }

}
