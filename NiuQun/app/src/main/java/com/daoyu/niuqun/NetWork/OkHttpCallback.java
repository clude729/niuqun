package com.daoyu.niuqun.NetWork;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by clude on 2018/10/10.
 */

public interface OkHttpCallback {

    void onSuccess(JSONObject oriData);

    void onFailure(IOException e);

}
