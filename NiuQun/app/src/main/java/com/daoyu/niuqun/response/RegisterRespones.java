package com.daoyu.niuqun.response;

import com.daoyu.niuqun.bean.AccountInfo;

/**
 * 注册
 */

public class RegisterRespones extends BaseResponse
{

    private static final String TAG = "RegisterRespones";

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
