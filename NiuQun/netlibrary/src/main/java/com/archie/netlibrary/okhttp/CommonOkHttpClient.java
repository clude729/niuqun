package com.archie.netlibrary.okhttp;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import com.archie.netlibrary.okhttp.https.HttpsUtils;
import com.archie.netlibrary.okhttp.listener.DisposeDataHandle;
import com.archie.netlibrary.okhttp.response.CommonJsonCallback;

/**
 * 项目名:   NetTest2
 * 包名:     com.archie.netlibrary.okhttp
 * 文件名:   CommonOkHttpClient
 * 创建者:   Jarchie
 * 创建时间: 17/12/13 上午10:25
 * 描述:     请求的发送，请求参数的配置，https支持
 */
public class CommonOkHttpClient
{

    private static final int TIME_OUT = 15; //超时参数
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static OkHttpClient mOkHttpClient;

    //为我们的Client配置参数，使用静态语句块来配置
    static
    {
        //创建我们Client对象的构建者
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.cookieJar(new CookieJar()
        {
            @Override
            public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies)
            {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(@NonNull HttpUrl url)
            {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        })
            //为构建者填充超时时间
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            //允许重定向
            .followRedirects(true)
            //添加https支持
            .hostnameVerifier(new HostnameVerifier()
            {
                @Override
                public boolean verify(String s, SSLSession sslSession)
                {
                    return true;
                }
            }).sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());
        mOkHttpClient = okHttpBuilder.build();
    }

    //发送具体的HTTP以及Https请求
    public static Call sendRequest(Request request, CommonJsonCallback commonCallback)
    {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(commonCallback);
        return call;
    }

    //GET请求
    public static Call get(Request request, DisposeDataHandle handle)
    {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    //POST请求
    public static Call post(Request request, DisposeDataHandle handle)
    {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

}
