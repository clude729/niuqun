package com.daoyu.niuqun.ui;

import java.io.File;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.util.Logger;

/**
 * 下载安装包
 */
public class DownAPKService extends Service
{

    private static final String TAG = "DownAPKService";

    // 文件保存路径(如果有SD卡就保存SD卡,如果没有SD卡就保存到手机包名下的路径)
    private String APK_dir = "";

    private long apkId;

    private DownLoadCompleteReceiver completeReceiver;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Logger.d(TAG, "onCreate");
        // 创建保存路径
        initAPKDir();
        completeReceiver = new DownLoadCompleteReceiver();
        registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Logger.d(TAG, "onStartCommand, intent: " + intent);
        //文件下载路径
        String APK_url = intent.getStringExtra("apk_url");
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(APK_url));
        String title = getResources().getString(R.string.app_name);
        request.setTitle(title);
        String des = getResources().getString(R.string.download_now) + title;
        request.setDescription(des);
        //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(Uri.fromFile(new File(APK_dir + "niuqun.apk")));
        //request.setDestinationInExternalPublicDir(APK_dir, "subei.apk");
        //request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "subei.apk");
        apkId = downloadManager.enqueue(request);
        //request.allowScanningByMediaScanner();
        return super.onStartCommand(intent, flags, startId);
    }

    private class DownLoadCompleteReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction()))
            {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == apkId)
                {
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.fromFile(new File(APK_dir + "niuqun.apk"));
                    installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                    installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(installIntent);
                    stopSelf();
                }
            }
            else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction()))
            {
                Logger.d(TAG, "DownLoadCompleteReceiver, ACTION_NOTIFICATION_CLICKED!");
            }
        }
    }

    private void initAPKDir()
    {
        //创建路径的时候一定要用[/],不能使用[\],但是创建文件夹加文件的时候可以使用[\]. [/]符号是Linux系统路径分隔符,而[\]是windows系统路径分隔符 Android内核是Linux.
        // 判断是否插入SD卡
        if (isHasSdcard())
        {
            // 保存到SD卡路径下
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + HttpConstant.VERSION_PATH;
        }
        else
        {
            // 保存到app的包名路径下
            APK_dir = getApplicationContext().getFilesDir().getAbsolutePath() + HttpConstant.VERSION_PATH;
        }
        File destDir = new File(APK_dir);

        if (!destDir.exists())
        {
            // 判断文件夹是否存在
            destDir.mkdirs();
        }
    }

    /**
     *
     * 判断是否插入SD卡
     *
     * @return boolean
     */
    private boolean isHasSdcard()
    {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onDestroy()
    {
        unregisterReceiver(completeReceiver);
        super.onDestroy();
        stopSelf();
    }

}
