package com.daoyu.niuqun.util;

import android.text.TextUtils;

/**
 * 字符串工具类
 */

public class StringUtil
{

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles)
    {
        /*
         * 移动：134、135、136、137、138、139、147、148、150、151、152、157(TD)、158、159、165、172、178、182、183、184、187、188、198
         * 联通：130、131、132、145、146、155、156、166、171、175、176、185、186
         * 电信：133、149、153、173、180、181、189、199（1349卫通）
         * 虚拟运营商：170
         * 总结起来就是第一位必定为1，第二位必定为3或4或5或6或7或8或9，其他位置的可以为0-9
         */
        //"[1]"代表第1位为数字1，"[3456789]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3456789]\\d{9}";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

}
