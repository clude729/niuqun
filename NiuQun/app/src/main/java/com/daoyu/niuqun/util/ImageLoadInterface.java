package com.daoyu.niuqun.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;

/**
 * 图片加载接口 Created by clude on 2018/8/20.
 */

public interface ImageLoadInterface
{

    void load(Context context, ImageView imageView, String url);

    void load(Context context, ImageView imageView, String url, RequestOptions requestOptions);

    void loadAsBitmap(Context context, ImageView imageView, String url);

    void loadAsBitmap(Context context, ImageView imageView, String url, RequestOptions requestOptions);

    void load(Context context, ImageView imageView, int resourceId);

    void load(Context context, ImageView imageView, int resourceId, RequestOptions requestOptions);

}
