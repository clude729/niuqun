package com.daoyu.chat.server.utils.json;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;

import java.util.List;

import com.daoyu.chat.server.network.http.HttpException;

/**
 * [JSON解析管理类]
 *
 * @author huxinwu
 * @version 1.0
 *
 **/
public class JsonMananger
{

    static
    {
        TypeUtils.compatibleWithJavaBean = true;
    }
    private static final String tag = "JsonMananger";

    /**
     * 将json字符串转换成java对象
     * 
     * @param json String
     * @param cls class
     * @return class
     * @throws HttpException exception
     */
    public static <T> T jsonToBean(String json, Class<T> cls) throws HttpException
    {
        return JSON.parseObject(json, cls);
    }

    /**
     * 将json字符串转换成java List对象
     * 
     * @param json String
     * @param cls class
     * @return List
     * @throws HttpException exception
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) throws HttpException
    {
        return JSON.parseArray(json, cls);
    }

    /**
     * 将bean对象转化成json字符串
     * 
     * @param obj object
     * @return String
     * @throws HttpException exception
     */
    public static String beanToJson(Object obj) throws HttpException
    {
        String result = JSON.toJSONString(obj);
        Log.e(tag, "beanToJson: " + result);
        return result;
    }

}
