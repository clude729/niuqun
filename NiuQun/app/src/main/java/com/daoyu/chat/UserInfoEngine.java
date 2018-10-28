package com.daoyu.chat;

import android.content.Context;
import android.net.Uri;


import com.daoyu.chat.server.SealAction;
import com.daoyu.chat.server.network.async.AsyncTaskManager;
import com.daoyu.chat.server.network.async.OnDataListener;
import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.GetUserInfoByIdResponse;
import com.daoyu.niuqun.constant.HttpConstant;

import io.rong.imlib.model.UserInfo;

/**
 * 用户信息提供者的异步请求类
 * Created by AMing on 15/12/10.
 * Company RongCloud
 */
public class UserInfoEngine implements OnDataListener {


    private static UserInfoEngine instance;
    private UserInfoListener mListener;
    private Context context;

    public static UserInfoEngine getInstance(Context context) {
        if (instance == null) {
            instance = new UserInfoEngine(context);
        }
        return instance;
    }

    private UserInfoEngine(Context context) {
        this.context = context;
    }


    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    private static final int REQUSERINFO = 4234;

    public void startEngine(String userid) {
        setUserid(userid);
        AsyncTaskManager.getInstance(context).request(userid, REQUSERINFO, this);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        return new SealAction(context).getUserInfoById(id);
    }

    @Override
    public void onSuccess(int requestCode, Object result)
    {
        if (result != null)
        {
            GetUserInfoByIdResponse res = (GetUserInfoByIdResponse) result;
            if (HttpConstant.SUCCESS.equals(res.getCode()))
            {
                UserInfo userInfo = new UserInfo(res.getResult().getUser_id(), res.getResult().getUser_name(),
                    Uri.parse(res.getResult().getAvatar()));
                if (mListener != null)
                {
                    mListener.onResult(userInfo);
                }
            }
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        if (mListener != null) {
            mListener.onResult(null);
        }
    }

    public void setListener(UserInfoListener listener) {
        this.mListener = listener;
    }

    public interface UserInfoListener {
        void onResult(UserInfo info);
    }
}
