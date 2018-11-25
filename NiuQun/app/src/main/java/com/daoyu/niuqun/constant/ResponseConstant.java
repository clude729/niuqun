package com.daoyu.niuqun.constant;

/**
 * 网络请求状态码
 */

public class ResponseConstant {

    private static final int BASE = 30000;

    public static final int FEED_BACK = BASE + 1;

    public static final int UPDATA_AVATAR = BASE + 2;

    public static final int UPDATA_NAME = BASE + 3;

    public static final int UPDATA_SIGN= BASE + 4;

    public static final int UPDATA_NUMBER = BASE + 5;

    public static final int GOODS_DETAIL = BASE + 6;

    public static final int BRANDS_SCORE = BASE + 7;

    public static final int GOODS_ADD_CART = BASE + 8;

    public static final int GET_CART_LIST = BASE + 9;

    public static final int DEL_GOODS_FROM_CART = BASE + 10;

    public static final int BUY_GOODS_FROM_CART = BASE + 11;

    public static final int GET_ADDRESS_LIST = BASE + 12;

    public static final int SET_DEFAULT_ADDRESS = BASE + 13;

    public static final int DEL_ADDRESS = BASE + 14;

    public static final int EDIT_ADDRESS = BASE + 15;

    public static final int CREAT_NEW_ADDRESS = BASE + 16;

    public static final int GET_ADDRESS_DETAIL = BASE + 17;

    public static final int SEND_ORDER_FROM_CART = BASE + 18;

    public static final int ORDER_TO_PAY = BASE + 19;

    public static final int RECHARGE_TO_PAY = BASE + 20;

    public static final int GET_ALIPAY = BASE + 21;

    public static final int GET_PAY_RESULT = BASE + 22;

    public static final int GOODS_BUY_SINGLE = BASE + 23;

    public static final int GET_WXPAY = BASE + 24;

    public static final int GET_CHANGE_BALANCE = BASE + 25;

    public static final int GET_CHANGE_DETAIL = BASE + 26;

    public static final int UPDATA_NICKNAME = BASE + 27;

    public static final int UPDATA_NIUQUN_NUM = BASE + 28;

    public static final int UPDATA_PERSON_SIGN = BASE + 29;

    public static final int APP_MESSAGE_LIST = BASE + 30;

    public static final int APP_MESSAGE_DETAIL = BASE + 31;

}
