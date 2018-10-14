package com.daoyu.niuqun.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * 广播管理类
 */

public class BroadcastManagerUtil
{

    private static final String TAG = "BroadcastManagerUtil";

    /**
     * 发送权限广播
     *
     * @param context 上下文对象
     * @param intent intent
     * @param receiverPermission receiverPermission
     * @param setInternalPackageName setInternalPackageName
     */
    public static final void sendBroadcastPermission(Context context, Intent intent, String receiverPermission,
        boolean setInternalPackageName)
    {
        if (setInternalPackageName)
        {
            intent.setPackage(context.getPackageName());
        }
        Logger.d(TAG, "sendBroadcastPermission" + intent.getAction());
        context.sendBroadcast(intent, receiverPermission);
    }

    /**
     * 注册权限广播
     *
     * @param context 上下文对象
     * @param receiver 广播接收者
     * @param filter intentfilter
     * @param receiverPermission 接收权限
     */
    public static final void registerReceiverPermission(Context context, BroadcastReceiver receiver,
        IntentFilter filter, String receiverPermission)
    {
        context.registerReceiver(receiver, filter, receiverPermission, null);
    }

    /**
     * 检测外部package广播是否为发送给本程序包的，还是发的是任务package广播
     *
     * @param context 上下文对象
     * @param intent intent
     * @return boolean
     *         <li>true 其广播intent有设置package为本引用packageName
     *         <li>false 其广播intent未设置package(intent.setPackage("***"))
     */
    public static final boolean checkBroadcastPackage(Context context, Intent intent)
    {
        boolean res = false;

        if (context.getPackageName().equals(intent.getPackage()))
        {
            res = true;
        }

        return res;
    }

    /**
     * 本地同进程局域广播发送(注意，此广播发送和接收端需在同一进程使用。同一应用，不同进程间广播无法传递)
     *
     * @param context 上下文对象
     * @param intent intent
     */
    public static final void sendBroadcast(Context context, Intent intent)
    {
        Logger.d(TAG, "sendBroadcast" + intent.getAction());
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(context);
        if (instance != null)
        {
            instance.sendBroadcast(intent);
        }
    }

    /**
     * 注册本地局域Receiver(注意，此广播发送和接收端需在同一进程使用。同一应用，不同进程间广播无法传递)
     *
     * @param context 上下文对象
     * @param receiver 广播接收者
     * @param filter filter
     */
    public static final void registerReceiver(Context context, BroadcastReceiver receiver, IntentFilter filter)
    {
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(context);
        if (instance != null)
        {
            instance.registerReceiver(receiver, filter);
        }
    }

    /**
     * 解除注册本地局域Receiver(注意，此广播发送和接收端需在同一进程使用。同一应用，不同进程间广播无法传递)
     *
     * @param context 上下文对象
     * @param receiver 广播接收者
     */
    public static final void unregisterReceiver(Context context, BroadcastReceiver receiver)
    {
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(context);
        if (instance != null)
        {
            instance.unregisterReceiver(receiver);
        }
    }

}
