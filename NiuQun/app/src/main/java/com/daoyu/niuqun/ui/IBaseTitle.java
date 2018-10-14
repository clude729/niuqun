package com.daoyu.niuqun.ui;

import android.view.View;

/**
 * 标题栏接口
 */
public interface IBaseTitle
{

    void onStartImageViewClicked(View view);

    void onEndImageViewClicked(View view);

    void onStartTextViewClicked(View view);

    void onEndTextViewClicked(View view);

    void onBackClicked();
}
