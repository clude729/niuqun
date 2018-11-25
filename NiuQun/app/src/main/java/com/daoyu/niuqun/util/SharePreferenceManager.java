package com.daoyu.niuqun.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP管理类
 */
public class SharePreferenceManager
{

    private static SharedPreferences sp;

    public static void init(Context context, String name)
    {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 用户昵称
     */
    private static final String KEY_CACHED_USERNAME = "cached_username";

    public static void setCachedUsername(String username)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_USERNAME, username).apply();
        }
    }

    public static String getCachedUsername()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_USERNAME, "");
        }
        return "";
    }

    /**
     * 密码
     */
    private static final String KEY_CACHED_PASSWORD = "cached_password";

    public static void setKeyCachedPassword(String password)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_PASSWORD, password).apply();
        }
    }

    public static String getKeyCachedPassword()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_PASSWORD, "");
        }
        return "";
    }

    /**
     * 用户id
     */
    private static final String KEY_CACHED_USERID = "cached_userid";

    public static void setKeyCachedUserid(String userid)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_USERID, userid).apply();
        }
    }

    public static String getKeyCachedUserid()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_USERID, "");
        }
        return "";
    }

    /**
     * 特助id
     */
    private static final String KEY_CACHED_REUSERID = "cached_reuserid";

    public static void setKeyCachedReUserid(String userid)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_REUSERID, userid).apply();
        }
    }

    public static String getKeyCachedReUserid()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_REUSERID, "");
        }
        return "";
    }

    /**
     * 用户头像
     */
    private static final String KEY_CACHED_AVATAR_PATH = "cached_avatar_path";

    public static void setCachedAvatarPath(String path)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_AVATAR_PATH, path).apply();
        }
    }

    public static String getCachedAvatarPath()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_AVATAR_PATH, "");
        }
        return "";
    }

    /**
     * 用户账号
     */
    private static final String KEY_CACHED_MOBILE = "cached_user_mobile";

    public static void setCacheMobile(String path)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_MOBILE, path).apply();
        }
    }

    public static String getCacheMobile()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_MOBILE, "");
        }
        return "";
    }

    /**
     * 牛群号
     */
    private static final String KEY_CACHED_HERDNO = "cached_user_app_number";

    public static void setCacheHerdNo(String path)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_HERDNO, path).apply();
        }
    }

    public static String getCacheHerdNo()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_HERDNO, "");
        }
        return "";
    }

    /**
     * 签名
     */
    private static final String KEY_CACHED_SIGN = "cached_user_sign";

    public static void setCacheSign(String path)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_SIGN, path).apply();
        }
    }

    public static String getCacheSign()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_SIGN, "");
        }
        return "";
    }

    /**
     * 上次打开引导页的版本号
     */
    private static final String KEY_CACHED_VERSION = "cached_version";

    public static void setKeyCachedVersion(String version)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_VERSION, version).apply();
        }
    }

    public static String getKeyCachedVersion()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_VERSION, "");
        }
        return "";
    }

    /**
     * 推送设置
     */
    private static final String KEY_CACHED_PUSH_SET = "cached_push_set";

    public static void setKeyCachedPushSet(String pushSet)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_PUSH_SET, pushSet).apply();
        }
    }

    public static String getKeyCachedPushSet()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_PUSH_SET, "");
        }
        return "";
    }

    /**
     * 极光注册id
     */
    private static final String KEY_CACHED_JIGUANG = "cached_jiguang";

    public static void setKeyCachedJiguang(String jiguang)
    {
        if (null != sp)
        {
            sp.edit().putString(KEY_CACHED_JIGUANG, jiguang).apply();
        }
    }

    public static String getKeyCachedJiguang()
    {
        if (null != sp)
        {
            return sp.getString(KEY_CACHED_JIGUANG, "");
        }
        return "";
    }

    /**
     * 存储
     * 
     * @param key 键名
     * @param value 值
     */
    public static void setKeyStringValue(String key, String value)
    {
        if (null != sp)
        {
            sp.edit().putString(key, value).apply();
        }
    }

    /**
     * 提取
     * 
     * @param key 键名
     * @return 值
     */
    public static String getKeyStringValue(String key)
    {
        if (null != sp)
        {
            return sp.getString(key, "");
        }
        return "";
    }

    /**
     * 存储
     * 
     * @param key 键名
     * @param value 值
     */
    public static void setKeyIntValue(String key, int value)
    {
        if (null != sp)
        {
            sp.edit().putInt(key, value).apply();
        }
    }

    /**
     * 提取
     * 
     * @param key 键名
     * @return 值
     */
    public static int getKeyIntValue(String key)
    {
        return getKeyIntValue(key, -1);
    }

    /**
     * 提取
     *
     * @param key 键名
     * @param def 默认值
     * @return 值
     */
    public static int getKeyIntValue(String key, int def)
    {
        if (null != sp)
        {
            return sp.getInt(key, def);
        }
        return def;
    }

    /**
     * 存储
     * 
     * @param key 键名
     * @param value 值
     */
    public static void setKeyBooleanValue(String key, boolean value)
    {
        if (null != sp)
        {
            sp.edit().putBoolean(key, value).apply();
        }
    }

    /**
     * 提取
     * 
     * @param key 键名
     * @return 值
     */
    public static boolean getKeyBooleanValue(String key)
    {
        return null != sp && sp.getBoolean(key, false);
    }

    /**
     * 存储
     * 
     * @param key 键名
     * @param value 值
     */
    public static void setKeyFloatValue(String key, float value)
    {
        if (null != sp)
        {
            sp.edit().putFloat(key, value).apply();
        }
    }

    /**
     * 提取
     * 
     * @param key 键名
     * @return 值
     */
    public static float getKeyFloatValue(String key)
    {
        if (null != sp)
        {
            return sp.getFloat(key, -1);
        }
        return -1;
    }

}
