package com.daoyu.niuqun.ui;

import android.app.Application;

import com.daoyu.niuqun.BuildConfig;
import com.daoyu.niuqun.constant.SharePreferenceConstant;
import com.daoyu.niuqun.util.FileHelper;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.SharePreferenceManager;

public class App extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        if (BuildConfig.DEBUG)
        {
            Logger.setDebug(true);
        }
        SharePreferenceManager.init(this, SharePreferenceConstant.SP_NAME);
        FileHelper.creatImagePath(SharePreferenceConstant.IMAGE_PATH);
    }
}
