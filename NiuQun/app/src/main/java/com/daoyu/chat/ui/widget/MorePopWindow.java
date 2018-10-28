package com.daoyu.chat.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.LinearLayout;

import com.daoyu.chat.ui.activity.SearchFriendActivity;
import com.daoyu.chat.ui.activity.SelectFriendsActivity;
import com.daoyu.niuqun.R;

/**
 * 聊天下拉框，包括发起群聊、添加朋友、扫一扫、收付款
 */
public class MorePopWindow extends PopupWindow
{

    @SuppressLint("InflateParams")
    public MorePopWindow(final Activity context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View content = inflater.inflate(R.layout.popupwindow_add, null);

        // 设置SelectPicPopupWindow的View
        this.setContentView(content);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        //发起群聊
        LinearLayout re_chatroom = content.findViewById(R.id.re_chatroom);
        //添加朋友
        LinearLayout re_addfriends = content.findViewById(R.id.re_addfriends);
        //扫一扫
        LinearLayout re_scanner = content.findViewById(R.id.re_scanner);
        //收付款
        LinearLayout re_payment = content.findViewById(R.id.re_payment);

        re_chatroom.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(new Intent(context, SelectFriendsActivity.class));
                intent.putExtra("createGroup", true);
                context.startActivity(intent);
                MorePopWindow.this.dismiss();
            }

        });

        re_addfriends.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                context.startActivity(new Intent(context, SearchFriendActivity.class));
                MorePopWindow.this.dismiss();
            }

        });

        re_scanner.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MorePopWindow.this.dismiss();
            }
        });

        re_payment.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MorePopWindow.this.dismiss();
            }
        });
    }

    /**
     * 显示popupWindow
     *
     * @param parent view
     */
    public void showPopupWindow(View parent)
    {
        if (!this.isShowing())
        {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        }
        else
        {
            this.dismiss();
        }
    }
}
