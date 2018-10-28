package com.daoyu.chat.server.network.async;


import com.daoyu.chat.server.network.http.HttpException;

public interface OnDataListener {


    /**
     * 异步耗时方法
     * @param  parameter 请求传参,可不填
     * @param requestCode 请求码
     * @return object
     * @throws HttpException exception
     */
    Object doInBackground(int requestCode, String parameter) throws HttpException;
    /**
     * 成功方法（可直接更新UI）
     * @param requestCode 请求码
     * @param result 返回结果
     */
    void onSuccess(int requestCode, Object result);

    /**
     * 失败方法（可直接更新UI）
     * @param requestCode 请求码
     * @param state 返回状态
     * @param result 返回结果
     */
    void onFailure(int requestCode, int state, Object result);
}