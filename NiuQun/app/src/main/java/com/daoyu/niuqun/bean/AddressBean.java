package com.daoyu.niuqun.bean;

/**
 * 收货地址
 */

public class AddressBean
{
    private static final String TAG = "AddressBean";

    private String add_id;

    private String user_id;

    private String real_name;

    private String mobile;

    private String province;

    private String city;

    private String district;

    private String address;

    private String status;

    public String getAdd_id()
    {
        return add_id;
    }

    public void setAdd_id(String add_id)
    {
        this.add_id = add_id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getReal_name()
    {
        return real_name;
    }

    public void setReal_name(String real_name)
    {
        this.real_name = real_name;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String toString()
    {
        return TAG + "{addId = " + add_id + " ,address = " + address + "}";
    }

}
