package com.daoyu.niuqun.ui.chat;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.ui.brand.BrandFragment;
import com.daoyu.niuqun.ui.center.MyCenterFragment;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.chat.ui.widget.DragPointView;
import com.daoyu.chat.ui.widget.DragPointView.OnDragListener;
import com.daoyu.chat.ui.widget.MorePopWindow;
import com.daoyu.niuqun.util.ViewUtil;

public class PhoneMainActivity extends FragmentActivity
    implements OnClickListener, OnPageChangeListener, OnDragListener, IUnReadMessageObserver
{

    private static final String TAG = "PhoneMainActivity";

    //防止快速点击
    private long firstClick = 0;

    private long secondClick = 0;

    private Toast mToast;

    private TextView tvAddress;

    private TextView tvCircle;

    private ImageView ivAdd;

    private ImageView ivSearch;

    public static ViewPager viewParent;

    private RelativeLayout rlChat;

    private TextView tvChat;

    private ImageView ivChat;

    private DragPointView mUnreadNumView;

    private LinearLayout llBrands;

    private TextView tvBrands;

    private ImageView ivBrands;

    private LinearLayout llMy;

    private TextView tvMy;

    private ImageView ivMy;

    private List<Fragment> mFragment = new ArrayList<>();

    private ConversationListFragment mConversationListFragment = null;

    private Conversation.ConversationType[] mConversationsTypes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_main);
        initView();
        setTranslucentStatus(true);
        initListener();
        initMainViewPager();
    }

    private void initView()
    {
        viewParent = findViewById(R.id.main_viewpager);
        rlChat = findViewById(R.id.rl_chat);
        tvChat = findViewById(R.id.text_chat);
        ivChat = findViewById(R.id.iv_chat);
        mUnreadNumView = findViewById(R.id.chat_num);
        llBrands = findViewById(R.id.ll_brands);
        tvBrands = findViewById(R.id.text_brands);
        ivBrands = findViewById(R.id.iv_brands);
        llMy = findViewById(R.id.ll_my);
        tvMy = findViewById(R.id.text_my);
        ivMy = findViewById(R.id.iv_my);
        tvAddress = findViewById(R.id.tv_book);
        tvCircle = findViewById(R.id.tv_circle);
        ivAdd = findViewById(R.id.iv_add);
        ivSearch = findViewById(R.id.iv_search);
    }

    private void initListener()
    {
        rlChat.setOnClickListener(this);
        llBrands.setOnClickListener(this);
        llMy.setOnClickListener(this);
        mUnreadNumView.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvCircle.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
    }

    private void initMainViewPager()
    {
        Fragment conversationList = initConversationList();
        mFragment.add(conversationList);
        mFragment.add(new BrandFragment());
        mFragment.add(new MyCenterFragment());
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return mFragment.get(position);
            }

            @Override
            public int getCount()
            {
                return mFragment.size();
            }
        };
        viewParent.setAdapter(fragmentPagerAdapter);
        viewParent.setOffscreenPageLimit(3);
        viewParent.addOnPageChangeListener(this);
        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, mConversationsTypes);
    }

    private Fragment initConversationList()
    {
        if (mConversationListFragment == null)
        {
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon().appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                .build();
            mConversationsTypes = new Conversation.ConversationType[] {Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.PUBLIC_SERVICE,
                Conversation.ConversationType.APP_PUBLIC_SERVICE, Conversation.ConversationType.SYSTEM };
            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
        }
        return mConversationListFragment;
    }

    protected void setTranslucentStatus(boolean on)
    {
        View barView = findViewById(R.id.barview);
        if (null == barView)
        {
            return;
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) barView.getLayoutParams();
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
            params.width = ViewUtil.getIntance().getDisplayWidth(this);
            params.height = ViewUtil.getIntance().getStatusBarHeight(this);
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
            params.width = ViewUtil.getIntance().getDisplayWidth(this);
            params.height = ViewUtil.getIntance().getStatusBarHeight(this);
            win.setAttributes(winParams);
        }
        else
        {
            params.width = 0;
            params.height = 0;
        }
        barView.setLayoutParams(params);
    }

    private void changeTextViewColor()
    {
        ivChat.setImageResource(R.mipmap.liaotian);
        tvChat.setTextColor(getResources().getColor(R.color.color3));
        ivBrands.setImageResource(R.mipmap.pinpai);
        tvBrands.setTextColor(getResources().getColor(R.color.color3));
        ivMy.setImageResource(R.mipmap.my);
        tvMy.setTextColor(getResources().getColor(R.color.color3));
    }

    private void changeSelectedTabState(int position)
    {
        switch (position)
        {
            case 0:
                tvChat.setTextColor(getResources().getColor(R.color.color1));
                ivChat.setImageResource(R.mipmap.liaotian_press);
                break;
            case 1:
                tvBrands.setTextColor(getResources().getColor(R.color.color1));
                ivBrands.setImageResource(R.mipmap.pinpai_press);
                break;
            case 2:
                tvMy.setTextColor(getResources().getColor(R.color.color1));
                ivMy.setImageResource(R.mipmap.my_press);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.rl_chat:
                if (viewParent.getCurrentItem() == 0) {
                    if (firstClick == 0) {
                        firstClick = System.currentTimeMillis();
                    } else {
                        secondClick = System.currentTimeMillis();
                    }
                    Logger.i(TAG, "time = " + (secondClick - firstClick));
                    if (secondClick - firstClick > 0 && secondClick - firstClick <= 800) {
                        mConversationListFragment.focusUnreadItem();
                        firstClick = 0;
                        secondClick = 0;
                    } else if (firstClick != 0 && secondClick != 0) {
                        firstClick = 0;
                        secondClick = 0;
                    }
                }
                viewParent.setCurrentItem(0, false);
                break;
            case R.id.ll_brands:
                viewParent.setCurrentItem(1, false);
                break;
            case R.id.ll_my:
                viewParent.setCurrentItem(2, false);
                break;
            case R.id.tv_book:

                break;
            case R.id.tv_circle:

                break;
            case R.id.iv_add:
                MorePopWindow morePopWindow = new MorePopWindow(PhoneMainActivity.this);
                morePopWindow.showPopupWindow(ivAdd);
                break;
            case R.id.iv_search:

                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        changeTextViewColor();
        changeSelectedTabState(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    @Override
    public void onDragOut()
    {
        mUnreadNumView.setVisibility(View.GONE);
        Logger.d(TAG, "onDragOut, clear success!");
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>()
        {
            @Override
            public void onSuccess(List<Conversation> conversations)
            {
                if (conversations != null && conversations.size() > 0)
                {
                    for (Conversation c : conversations)
                    {
                        RongIM.getInstance().clearMessagesUnreadStatus(c.getConversationType(), c.getTargetId(), null);
                    }
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode e)
            {
                Logger.e(TAG, "onDragOut, ErrorCode: " + e);
            }
        }, mConversationsTypes);
    }

    @Override
    public void onCountChanged(int count)
    {
        if (count == 0)
        {
            mUnreadNumView.setVisibility(View.GONE);
        }
        else if (count > 0 && count < 100)
        {
            mUnreadNumView.setVisibility(View.VISIBLE);
            mUnreadNumView.setText(String.valueOf(count));
        }
        else
        {
            mUnreadNumView.setVisibility(View.VISIBLE);
            mUnreadNumView.setText(R.string.no_read_message);
        }
    }

    @Override
    protected void onDestroy()
    {
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(this);
        super.onDestroy();
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

}
