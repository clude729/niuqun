package com.daoyu.chat.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.daoyu.niuqun.R;
import com.daoyu.chat.server.SealAction;
import com.daoyu.chat.server.network.async.AsyncTaskManager;
import com.daoyu.chat.server.network.async.OnDataListener;
import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.niuqun.util.ViewUtil;

public abstract class BaseActivity extends FragmentActivity implements OnDataListener
{

    protected Context mContext;

    public AsyncTaskManager mAsyncTaskManager;

    protected SealAction action;

    private ViewFlipper mContentView;

    protected RelativeLayout mHeadLayout;

    protected Button mBtnLeft;

    protected Button mBtnRight;

    protected TextView mTitle;

    protected TextView mHeadRightText;

    private Drawable mBtnBackDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.layout_base);
        // 使得音量键控制媒体声音
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mContext = this;

        // 初始化公共头部
        mContentView = (ViewFlipper) super.findViewById(R.id.layout_container);
        mHeadLayout = (RelativeLayout) super.findViewById(R.id.layout_head);
        mHeadRightText = (TextView) findViewById(R.id.text_right);
        mBtnLeft = (Button) super.findViewById(R.id.btn_left);
        mBtnRight = (Button) super.findViewById(R.id.btn_right);
        mTitle = (TextView) super.findViewById(R.id.tv_title);
        mBtnBackDrawable = getResources().getDrawable(R.mipmap.icon_back);
        mBtnBackDrawable.setBounds(0, 0, mBtnBackDrawable.getMinimumWidth(), mBtnBackDrawable.getMinimumHeight());

        mAsyncTaskManager = AsyncTaskManager.getInstance(getApplicationContext());
        // Activity管理
        action = new SealAction(mContext);
        setTranslucentStatus();
    }

    @Override
    public void setContentView(View view)
    {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mContentView.addView(view, lp);
    }

    @Override
    public void setContentView(int layoutResID)
    {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    protected void setTranslucentStatus()
    {
        View barview = findViewById(R.id.barview);
        if (null == barview)
        {
            return;
        }
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) barview.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            params.width = ViewUtil.getIntance().getDisplayWidth(this);
            params.height = ViewUtil.getIntance().getStatusBarHeight(this);
        }
        else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
        {
            bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            params.width = ViewUtil.getIntance().getDisplayWidth(this);
            params.height = ViewUtil.getIntance().getStatusBarHeight(this);
            win.setAttributes(winParams);
        }
        else
        {
            params.width = 0;
            params.height = 0;
        }
        barview.setLayoutParams(params);
    }

    /**
     * 设置头部是否可见
     *
     * @param visibility int
     */
    public void setHeadVisibility(int visibility)
    {
        mHeadLayout.setVisibility(visibility);
    }

    /**
     * 设置左边是否可见
     *
     * @param visibility int
     */
    public void setHeadLeftButtonVisibility(int visibility)
    {
        mBtnLeft.setVisibility(visibility);
    }

    /**
     * 设置右边是否可见
     *
     * @param visibility int
     */
    public void setHeadRightButtonVisibility(int visibility)
    {
        mBtnRight.setVisibility(visibility);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId)
    {
        setTitle(getString(titleId), false);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId, boolean flag)
    {
        setTitle(getString(titleId), flag);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title)
    {
        setTitle(title, false);
    }

    /**
     * 设置标题
     *
     * @param title String
     * @param flag boolean
     */
    public void setTitle(String title, boolean flag)
    {
        mTitle.setText(title);
        if (flag)
        {
            mBtnLeft.setCompoundDrawables(null, null, null, null);
        }
        else
        {
            mBtnLeft.setCompoundDrawables(mBtnBackDrawable, null, null, null);
        }
    }

    /**
     * 点击左按钮
     */
    public void onHeadLeftButtonClick(View v)
    {
        finish();
    }

    /**
     * 点击右按钮
     */
    public void onHeadRightButtonClick(View v)
    {

    }

    /**
     * 点击右边textview
     * 
     * @param view view
     */
    public void onHeadRightTextViewClick(View view)
    {

    }

    public Button getHeadLeftButton()
    {
        return mBtnLeft;
    }

    public void setHeadLeftButton(Button leftButton)
    {
        this.mBtnLeft = leftButton;
    }

    public Button getHeadRightButton()
    {
        return mBtnRight;
    }

    public TextView getHeadRightTextView()
    {
        return mHeadRightText;
    }

    public void setHeadRightButton(Button rightButton)
    {
        this.mBtnRight = rightButton;
    }

    public Drawable getHeadBackButtonDrawable()
    {
        return mBtnBackDrawable;
    }

    public void setBackButtonDrawable(Drawable backButtonDrawable)
    {
        this.mBtnBackDrawable = backButtonDrawable;
    }

    protected void onResume()
    {
        super.onResume();
    }

    protected void onPause()
    {
        super.onPause();
    }

    /**
     * 发送请求（需要检查网络）
     *
     * @param requestCode 请求码
     */
    public void request(int requestCode)
    {
        if (mAsyncTaskManager != null)
        {
            mAsyncTaskManager.request(requestCode, this);
        }
    }

    /**
     * 发送请求（需要检查网络）
     *
     * @param id 请求数据的用户ID或者groupID
     * @param requestCode 请求码
     */
    public void request(String id, int requestCode)
    {
        if (mAsyncTaskManager != null)
        {
            mAsyncTaskManager.request(id, requestCode, this);
        }
    }

    /**
     * 发送请求
     *
     * @param requestCode 请求码
     * @param isCheckNetwork 是否需检查网络，true检查，false不检查
     */
    public void request(int requestCode, boolean isCheckNetwork)
    {
        if (mAsyncTaskManager != null)
        {
            mAsyncTaskManager.request(requestCode, isCheckNetwork, this);
        }
    }

    /**
     * 取消所有请求
     */
    public void cancelRequest()
    {
        if (mAsyncTaskManager != null)
        {
            mAsyncTaskManager.cancelRequest();
        }
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result)
    {

    }

    @Override
    public void onFailure(int requestCode, int state, Object result)
    {
        switch (state)
        {
            // 网络不可用给出提示
            case AsyncTaskManager.HTTP_NULL_CODE:
                NToast.shortToast(mContext, "当前网络不可用");
                break;

            // 网络有问题给出提示
            case AsyncTaskManager.HTTP_ERROR_CODE:
                NToast.shortToast(mContext, "网络问题请稍后重试");
                break;

            // 请求有问题给出提示
            case AsyncTaskManager.REQUEST_ERROR_CODE:
                // NToast.shortToast(mContext, R.string.common_request_error);
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (null != this.getCurrentFocus() && event.getAction() == MotionEvent.ACTION_UP)
        {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
