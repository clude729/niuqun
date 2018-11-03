package com.daoyu.niuqun.ui.center;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.daoyu.chat.SealConst;
import com.daoyu.chat.server.broadcast.BroadcastManager;
import com.daoyu.chat.server.widget.DialogWithYesOrNoUtils;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.ui.MyBaseActivity;

public class SettingActivity extends MyBaseActivity implements OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTranslucentStatus();
        initView();
    }

    private void initView()
    {
        updataTitle(getResources().getString(R.string.my_setting));
        updataStartImageView(R.mipmap.icon_back);
        LinearLayout ll_account = findViewById(R.id.ll_account);
        ll_account.setOnClickListener(this);

        LinearLayout ll_notify = findViewById(R.id.ll_notify);
        ll_notify.setOnClickListener(this);

        LinearLayout ll_privacy = findViewById(R.id.ll_privacy);
        ll_privacy.setOnClickListener(this);

        LinearLayout ll_feedback = findViewById(R.id.ll_feedback);
        ll_feedback.setOnClickListener(this);

        LinearLayout ll_about = findViewById(R.id.ll_about);
        ll_about.setOnClickListener(this);

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_account:

                break;
            case R.id.ll_notify:

                break;
            case R.id.ll_privacy:

                break;
            case R.id.ll_feedback:

                break;
            case R.id.ll_about:

                break;
            case R.id.btn_logout:
                DialogWithYesOrNoUtils.getInstance().showDialog(this, "是否退出登录?",
                    new DialogWithYesOrNoUtils.DialogCallBack()
                    {
                        @Override
                        public void executeEvent()
                        {
                            BroadcastManager.getInstance(SettingActivity.this).sendBroadcast(SealConst.EXIT);
                        }

                        @Override
                        public void executeEditEvent(String editText)
                        {

                        }

                        @Override
                        public void updatePassword(String oldPassword, String newPassword)
                        {

                        }
                    });
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartImageViewClicked(View view)
    {
        super.onStartImageViewClicked(view);
        onBackClicked();
        finish();
    }
}
