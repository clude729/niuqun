package com.daoyu.niuqun.ui;

import java.util.List;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.daoyu.chat.SealAppContext;
import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.niuqun.BuildConfig;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AddressBean;
import com.daoyu.niuqun.bean.CartGoodsInfo;
import com.daoyu.niuqun.constant.SharePreferenceConstant;
import com.daoyu.niuqun.util.FileHelper;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.SharePreferenceManager;

import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.display.FadeInBitmapDisplayer;
import io.rong.imkit.RongIM;

public class App extends Application
{

    private static DisplayImageOptions options;

    private Context context;

    private AddressBean addressBean;

    private List<CartGoodsInfo> cartGoodsInfos;

    @Override
    public void onCreate()
    {
        super.onCreate();
        setContext(this);
        if (BuildConfig.DEBUG)
        {
            Logger.setDebug(true);
        }
        SharePreferenceManager.init(this, SharePreferenceConstant.SP_NAME);
        FileHelper.creatImagePath(SharePreferenceConstant.IMAGE_PATH);
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())))
        {
            RongIM.setServerInfo("nav.cn.ronghub.com", "up.qbox.me");
            RongIM.init(this);
            SealUserInfoManager.init(this);
            SealAppContext.init(this);
        }
        options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_default_head)
            .showImageOnFail(R.drawable.ic_default_head).showImageOnLoading(R.drawable.ic_default_head)
            .displayer(new FadeInBitmapDisplayer(300)).cacheInMemory(true).cacheOnDisk(true).build();
    }

    public static DisplayImageOptions getOptions()
    {
        return options;
    }

    public static String getCurProcessName(Context context)
    {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses())
        {
            if (appProcess.pid == pid)
            {
                return appProcess.processName;
            }
        }
        return null;
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public AddressBean getAddressBean()
    {
        return addressBean;
    }

    public void setAddressBean(AddressBean addressBean)
    {
        this.addressBean = addressBean;
    }

    public List<CartGoodsInfo> getCartGoodsInfos()
    {
        return cartGoodsInfos;
    }

    public void setCartGoodsInfos(List<CartGoodsInfo> cartGoodsInfos)
    {
        this.cartGoodsInfos = cartGoodsInfos;
    }
}
