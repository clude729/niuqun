package com.daoyu.niuqun.bean;

/**
 * 版本信息
 */

public class VersionBean
{

    private static final String TAG = "VersionBean";

    private String version;

    private String upgrade;

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getUpgrade()
    {
        return upgrade;
    }

    public void setUpgrade(String upgrade)
    {
        this.upgrade = upgrade;
    }

    public String toString()
    {
        return TAG + ": { version = " + version + " }";
    }

}
