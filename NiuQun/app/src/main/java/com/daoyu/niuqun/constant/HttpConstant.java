package com.daoyu.niuqun.constant;

/**
 * 接口地址
 */
public class HttpConstant
{
    public static final String URL = "http://api.niu.lianbida.com/";

    public static final String SUCCESS = "000";

    //结算余额不足
    public static final String MONEY_NOT_ENOUGH = "003";

    //WebView解析编码
    public static final String WEB_TYPE="text/html; charset=utf-8";

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

    public static final String BRANDS_DETAIL = URL + "goods/detail";

    public static final String BRANDS_GET_SCORE_BY_ADS = URL + "goods/userclick";

    public static final String GOODS_ADD_SHOPPING_CART = URL + "goods/addcart";

    public static final String GOODS_BUY_SINGLE = URL + "goods/tobuysingle";

    public static final String BARNDS_GET_CART_LIST = URL + "goods/cartlist";

    public static final String BARNDS_CHOOSE_GOODS_OVER_FROM_CART = URL + "goods/toovercart";

    public static final String BARNDS_SEND_ORDER_BY_GOODS = URL + "goods/tocomfirm";

    public static final String BARNDS_TO_PAY_BY_ORDER = URL + "goods/topay";

    public static final String BARNDS_DEL_GOODS_FROM_CART = URL + "goods/";

    public static final String ORDER_TO_RECHARGE = URL + "user/torecharge";

    public static final String RECHARGE_BY_ALIPAY = URL + "pay/alipay";

    public static final String RECHARGE_BY_WEIXIN = URL + "pay/wxpay";

    public static final String RECHARGE_RESULT_BY_PAY = URL + "pay/payresult";

    public static final String GET_ADDRESS_LIST = URL + "user/myaddress";

    public static final String CREAT_NEW_ADDRESS = URL + "user/addaddress";

    public static final String SET_DEFAULT_ADDRESS = URL + "user/setdefault";

    public static final String GET_ADDREDD_DETAIL = URL + "user/getaddress";

    public static final String GET_MY_CHANGE = URL + "user/usermoney";

    public static final String GET_MY_CHANGE_DETAIL_LIST = URL + "user/userpaylog";

    public static final String GET_MY_ORDER_LIST = URL + "user/order";
}
