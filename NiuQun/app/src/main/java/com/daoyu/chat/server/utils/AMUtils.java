package com.daoyu.chat.server.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by AMing on 15/12/21.
 * Company RongCloud
 */
public class AMUtils
{

    /**
     * 手机号正则表达式
     **/
    private static final String MOBLIE_PHONE_PATTERN = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(18[0-9])|(19[0-9])|(17[0-9]))\\d{8}$";

    /**
     * 通过正则验证是否是合法手机号码
     *
     * @param phoneNumber 手机号码
     * @return boolean
     */
    public static boolean isMobile(String phoneNumber)
    {
        Pattern p = Pattern.compile(MOBLIE_PHONE_PATTERN);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static void onInactive(Context context, EditText et)
    {

        if (et == null)
            return;

        et.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public static void onActive(Context context, EditText et)
    {
        if (et == null)
            return;

        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, 0);

    }
}
