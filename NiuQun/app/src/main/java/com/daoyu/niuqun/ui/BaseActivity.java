package com.daoyu.niuqun.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.HandlerManager;

/**
 * 基类
 */

public class BaseActivity extends Activity implements IBaseTitle
{

    private Toast mToast;

    private OnClickListener mOnClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.text_start:
                    onStartTextViewClicked(v);
                    break;
                case R.id.text_end:
                    onEndTextViewClicked(v);
                    break;
                case R.id.image_start:
                    onStartImageViewClicked(v);
                    break;
                case R.id.image_end:
                    onEndImageViewClicked(v);
                    break;
                default:
                    break;
            }
        }
    };

    protected Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {

            handlerLogicMessage(msg);
        }
    };

    protected void handlerLogicMessage(Message msg)
    {
    }

    protected void setTranslucentStatus(boolean on)
    {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
        {
            bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on)
            {
                winParams.flags |= bits;
            }
            else
            {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    protected void updataTitle(String title)
    {
        if (null != findViewById(R.id.text_title))
        {
            ((TextView) findViewById(R.id.text_title)).setText(title);
        }
    }

    protected void updataStartTextView(String start)
    {
        if (null != findViewById(R.id.text_start))
        {
            TextView textView = findViewById(R.id.text_start);
            textView.setText(start);
            textView.setVisibility(View.VISIBLE);
            textView.setOnClickListener(mOnClickListener);
        }
    }

    protected void updataEndTextView(String end)
    {
        if (null != findViewById(R.id.text_end))
        {
            TextView textView = findViewById(R.id.text_end);
            textView.setText(end);
            textView.setVisibility(View.VISIBLE);
            textView.setOnClickListener(mOnClickListener);
        }
    }

    protected void updataStartImageView(int resId)
    {
        if (null != findViewById(R.id.image_start))
        {
            ImageView imageView = findViewById(R.id.image_start);
            imageView.setImageResource(resId);
            imageView.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(mOnClickListener);
        }
    }

    protected void updataEndImageView(int resId)
    {
        if (null != findViewById(R.id.image_end))
        {
            ImageView imageView = findViewById(R.id.image_end);
            imageView.setImageResource(resId);
            imageView.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(mOnClickListener);
        }
    }

    protected void showToast(String msg)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        else
        {
            mToast.setText(msg);
        }
        mToast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        HandlerManager.getInstance().register(handler);
        setTranslucentStatus(true);
    }

    @Override
    public void onStartImageViewClicked(View view)
    {

    }

    @Override
    public void onEndImageViewClicked(View view)
    {

    }

    @Override
    public void onStartTextViewClicked(View view)
    {

    }

    @Override
    public void onEndTextViewClicked(View view)
    {

    }

    @Override
    public void onBackClicked()
    {

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        HandlerManager.getInstance().unRegister(handler);
    }

    @Override
    public void onBackPressed()
    {
        onBackClicked();
        super.onBackPressed();
    }
}
