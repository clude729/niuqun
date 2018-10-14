package com.daoyu.niuqun.response;

import com.daoyu.niuqun.bean.AccountInfo;

/**
 * 登录
 */

public class LoginResponse extends BaseResponse
{

    private static final String TAG = "LoginResponse";

    private AccountInfo data;

    @Override
    public AccountInfo getData()
    {
        return data;
    }

    public void setData(AccountInfo data)
    {
        this.data = data;
    }

}
