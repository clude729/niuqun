package com.daoyu.niuqun.request;

import com.archie.netlibrary.okhttp.CommonOkHttpClient;
import com.archie.netlibrary.okhttp.listener.DisposeDataHandle;
import com.archie.netlibrary.okhttp.listener.DisposeDataListener;
import com.archie.netlibrary.okhttp.request.CommonRequest;
import com.archie.netlibrary.okhttp.request.RequestParams;
import com.daoyu.niuqun.response.LoginResponse;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.response.RegisterRespones;
import com.daoyu.niuqun.response.VefifyCodeResponse;

/**
 * 统一管理所有的请求
 */
public class RequestCenter
{

    //根据参数发送所有的post请求
    private static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz)
    {
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    //登录
    public static void requesetLoginData(RequestParams params, DisposeDataListener listener)
    {
        RequestCenter.postRequest(HttpConstant.USER_LOGIN, params, listener, LoginResponse.class);
    }

    //获取验证码
    public static void requesetGetCode(RequestParams params, DisposeDataListener listener)
    {
        RequestCenter.postRequest(HttpConstant.SMS_CODE, params, listener, VefifyCodeResponse.class);
    }

    //注册
    public static void requesetRegister(RequestParams params, DisposeDataListener listener)
    {
        RequestCenter.postRequest(HttpConstant.USER_REGISTER, params, listener, RegisterRespones.class);
    }

}
