package com.daoyu.niuqun.NetWork;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * 网络请求类
 */

public class HttpUtil
{

    private static final String TAG = "HttpUtil";

    //mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_JSON = MediaType
        .parse("application/x-www-form-urlencoded; charset=utf-8");
    //下面两个随便用哪个都可以    
    private static final MediaType MEDIA_TYPE_FILE = MediaType.parse("application/octet-stream");
    //private static final MediaType MEDIA_TYPE_FILE = MediaType.parse("image/jpg");

    private static HttpUtil instance;

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private OkHttpClient okHttpClient;

    private Handler okHttpHandler;

    private HttpUtil(Context context)
    {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.cookieJar(new CookieJar()
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
        });
        clientBuilder.readTimeout(HttpConfig.NETWORK_READ_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(HttpConfig.NETWORK_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(HttpConfig.NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.build();
        okHttpHandler = new Handler(context.getMainLooper());
    }

    public static HttpUtil getInstance(Context context)
    {
        if (null == instance)
        {
            synchronized (HttpUtil.class)
            {
                if (null == instance)
                {
                    instance = new HttpUtil(context);
                }
            }
        }
        return instance;
    }

}
