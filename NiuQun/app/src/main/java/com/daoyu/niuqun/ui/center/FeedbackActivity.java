package com.daoyu.niuqun.ui.center;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.constant.ResponseConstant;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener
{

    private static final String TAG = "FeedbackActivity";

    private EditText etFeedback;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle(R.string.help_and_feedback);
        etFeedback = findViewById(R.id.et_feedback);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.FEED_BACK:

                break;
        }
        return super.doInBackground(requestCode, id);
    }

    @Override
    public void onSuccess(int requestCode, Object result)
    {
        super.onSuccess(requestCode, result);
    }

    @Override
    public void onFailure(int requestCode, int state, Object result)
    {
        super.onFailure(requestCode, state, result);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_submit:
                String fbStr = etFeedback.getText().toString().trim();
                if (!TextUtils.isEmpty(fbStr))
                {
                    LoadDialog.show(mContext);
                    request(ResponseConstant.FEED_BACK, true);
                }
                break;
            default:
                break;
        }
    }
}
