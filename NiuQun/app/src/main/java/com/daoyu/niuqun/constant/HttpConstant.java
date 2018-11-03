package com.daoyu.niuqun.constant;

/**
 * 接口地址
 */
public class HttpConstant
{
    private static final String URL = "http://api.niu.lianbida.com/";

    public static final String SUCCESS = "000";

    public static final String USER_LOGIN = URL + "user/login";

    public static final String USER_REGISTER = URL + "user/reg";

    public static final String SMS_CODE = URL + "public/smscode";

    public static final String REG_PROVISION = URL + "public/provision";

    public static final String USER_UPDATE_USERNAME = URL + "user/updateusername";

    public static final String USER_UPDATE_AVATAR = URL + "user/updateavatar";

    public static final String USER_GET_PASSWORD = URL + "user/getpsw";

    public static final String USER_CENTER = URL + "user/center";

    public static final String CHAT_SEARCH_PERSON = URL + "user/getuserlist";

    public static final String CHAT_ADD_FRIEND = URL + "user/addfriends";

    public static final String CHAT_AGREE_OR_REFUSE_FRIEND = URL + "user/updatetomyfriends";

    public static final String CHAT_AGREE_OR_REFUSE_FRIEND_LIST = URL + "user/gettomyfriends";

    public static final String CHAT_MY_FRIEND_LIST = URL + "user/myfriends";

    public static final String BRANDS_LIST = URL + "goods/index";
}
