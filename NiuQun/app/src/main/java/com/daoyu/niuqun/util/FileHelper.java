package com.daoyu.niuqun.util;

import android.os.Environment;

import java.io.File;

/**
 * 文件路径类
 */
public class FileHelper
{

    private static FileHelper mInstance = new FileHelper();

    private static String SDPATH = "";

    public static FileHelper getInstance()
    {
        return mInstance;
    }

    public static boolean isSdCardExist()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void creatImagePath(String path)
    {
        SDPATH = Environment.getExternalStorageDirectory() + path;
        File desDir = new File(SDPATH);
        if (!desDir.exists())
        {
            desDir.mkdirs();
        }
    }

    public static String getSDPATH()
    {
        return SDPATH;
    }

}
