package com.daoyu.niuqun.constant;

/**
 * 接口地址
 */
public class HttpConstant
{
    private static final String URL = "http://api.niu.lianbida.com/";

    public static final String SUCCESS = "000";

    public static String USER_LOGIN = URL + "user/login";

    public static String USER_REGISTER = URL + "user/reg";

    public static String SMS_CODE = URL + "public/smscode";

    public static String REG_PROVISION = URL + "public/provision";

    public static String USER_UPDATE_USERNAME = URL + "user/updateusername";

    public static String USER_UPDATE_AVATAR = URL + "user/updateavatar";

    public static String USER_GET_PASSWORD = URL + "user/getpsw";

    public static String USER_CENTER = URL + "user/center";
}
