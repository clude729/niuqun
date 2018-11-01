package com.daoyu.niuqun.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * 图片加载 Created by clude on 2018/8/20.
 */

public class ImageLoad implements ImageLoadInterface
{

    private static volatile ImageLoad instance = null;

    private ImageLoad()
    {

    }

    public static ImageLoad getInstance()
    {
        if (null == instance)
        {
            synchronized (ImageLoad.class)
            {
                if (null == instance)
                {
                    instance = new ImageLoad();
                }
            }
        }

        return instance;
    }

    @Override
    public void load(Context context, ImageView imageView, String url)
    {
        Glide.with(context).load(url).into(imageView);
    }

    @Override
    public void load(Context context, ImageView imageView, String url, RequestOptions requestOptions)
    {
        Glide.with(context).load(url).apply(requestOptions).into(imageView);
    }

    @Override
    public void loadAsBitmap(Context context, ImageView imageView, String url)
    {
        Glide.with(context).asBitmap().load(url).into(imageView);
    }

    @Override
    public void loadAsBitmap(Context context, ImageView imageView, String url, RequestOptions requestOptions)
    {
        Glide.with(context).asBitmap().load(url).apply(requestOptions).into(imageView);
    }

    @Override
    public void load(Context context, ImageView imageView, int resourceId)
    {
        Glide.with(context).load(resourceId).into(imageView);
    }

    @Override
    public void load(Context context, ImageView imageView, int resourceId, RequestOptions requestOptions)
    {
        Glide.with(context).load(resourceId).apply(requestOptions).into(imageView);
    }
}
