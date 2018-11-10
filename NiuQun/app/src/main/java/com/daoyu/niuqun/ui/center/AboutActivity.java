package com.daoyu.niuqun.ui.center;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

/**
 * 关于牛群
 */
public class AboutActivity extends BaseActivity
{

    private static final String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.app_about);
        TextView tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(getResources().getString(R.string.new_version) + getVersionInfo()[1]);
    }

    private String[] getVersionInfo()
    {
        String[] version = new String[2];

        PackageManager packageManager = getPackageManager();

        try
        {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            version[0] = String.valueOf(packageInfo.versionCode);
            version[1] = packageInfo.versionName;
            return version;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Logger.d(TAG, "getVersionInfo, exception: " + e);
        }

        return version;
    }

}
