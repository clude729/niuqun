package com.daoyu.niuqun.util;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * handler管理类
 */

public class HandlerManager
{

    private static final HandlerManager INSTANCE = new HandlerManager();

    private final Object handlerLock = new Object();

    private List<Handler> handlers = new ArrayList<>();

    private HandlerManager()
    {
    }

    public static final HandlerManager getInstance()
    {
        return INSTANCE;
    }

    /**
     * 绑定handler
     *
     * @param handler handler
     */
    public void register(Handler handler)
    {
        synchronized (handlerLock)
        {
            if (null == handler)
            {
                return;
            }
            handlers.add(handler);
        }
    }

    /**
     * 解除绑定
     *
     * @param handler handler
     */
    public void unRegister(Handler handler)
    {
        synchronized (handlerLock)
        {
            if (null == handler)
            {
                return;
            }
            handlers.remove(handler);
        }
    }

    /**
     * 发送消息
     *
     * @param what 标识
     * @param data 数据
     */
    public void sendMessage(int what, Object data)
    {
        synchronized (handlerLock)
        {
            for (Handler handler : handlers)
            {
                Message message = handler.obtainMessage(what);
                message.obj = data;
                handler.sendMessage(message);
            }
        }
    }

    /**
     * 发送消息
     *
     * @param what 标记
     * @param arg1 arg1
     * @param data 数据
     */
    public void sendMessage(int what, int arg1, Object data)
    {
        synchronized (handlerLock)
        {
            for (Handler handler : handlers)
            {
                Message message = handler.obtainMessage(what);
                message.obj = data;
                message.arg1 = arg1;
                handler.sendMessage(message);
            }
        }
    }

    /**
     * 发送空消息
     *
     * @param what 标记
     */
    public void sendEmptyMessage(int what)
    {
        synchronized (handlerLock)
        {
            for (Handler handler : handlers)
            {
                Message message = handler.obtainMessage(what);
                handler.sendMessage(message);
            }
        }
    }

    /**
     * 延迟发送消息
     *
     * @param what 标记
     * @param data 消息
     * @param delay 延迟时间
     */
    public void sendMessageDelayed(int what, Object data, int delay)
    {
        synchronized (handlerLock)
        {
            for (Handler handler : handlers)
            {
                Message message = handler.obtainMessage(what);
                message.obj = data;
                handler.sendMessageDelayed(message, delay);
            }
        }
    }

    /**
     * 延迟发送空消息
     *
     * @param what 标记
     * @param delay 延迟时间
     */
    public void sendEmptyMessageDelayed(int what, int delay)
    {
        synchronized (handlerLock)
        {
            for (Handler handler : handlers)
            {
                Message message = handler.obtainMessage(what);
                handler.sendMessageDelayed(message, delay);
            }
        }
    }

}
