package com.daoyu.niuqun.ui.chat;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
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

import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.chat.db.Friend;
import com.daoyu.chat.server.pinyin.CharacterParser;
import com.daoyu.chat.server.widget.DialogWithYesOrNoUtils;
import com.daoyu.chat.ui.fragment.ContactsFragment;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.ReuserInfo;
import com.daoyu.niuqun.bean.VersionBean;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.ui.App;
import com.daoyu.niuqun.ui.DownAPKService;
import com.daoyu.niuqun.ui.brand.BrandFragment;
import com.daoyu.niuqun.ui.center.MyCenterFragment;
import com.daoyu.niuqun.ui.user.LoginActivity;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.chat.ui.widget.DragPointView;
import com.daoyu.chat.ui.widget.DragPointView.OnDragListener;
import com.daoyu.chat.ui.widget.MorePopWindow;
import com.daoyu.niuqun.util.SharePreferenceManager;
import com.daoyu.niuqun.util.ViewUtil;

public class PhoneMainActivity extends FragmentActivity
    implements OnClickListener, OnPageChangeListener, OnDragListener, IUnReadMessageObserver
{

    private static final String TAG = "PhoneMainActivity";

    //防止快速点击
    private long firstClick = 0;

    private long secondClick = 0;

    private Toast mToast;

    private TextView tvApp;

    private TextView tvAddress;

    private TextView tvCircle;

    private ImageView ivAdd;

    private ImageView ivSearch;

    private TextView tvBrandsGoods;

    private TextView tvNewGoods;

    public static ViewPager viewParent;

    private FrameLayout frameLayout;

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

    private ContactsFragment contactsFragment = null;

    private ConversationListFragment mConversationListFragment = null;

    private Conversation.ConversationType[] mConversationsTypes = null;
    
    private BrandsOrNewGoodsCallBack callBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_main);
        initView();
        setTranslucentStatus(true);
        initListener();
        initMainViewPager();
        getVersion();
    }

    private void initView()
    {
        viewParent = findViewById(R.id.main_viewpager);
        frameLayout = findViewById(R.id.frameLayout);
        frameLayout.setVisibility(View.GONE);
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
        tvApp = findViewById(R.id.tv_app);
        tvAddress = findViewById(R.id.tv_book);
        tvCircle = findViewById(R.id.tv_circle);
        ivAdd = findViewById(R.id.iv_add);
        ivSearch = findViewById(R.id.iv_search);
        tvBrandsGoods = findViewById(R.id.tv_brands_goods);
        tvNewGoods = findViewById(R.id.tv_new_goods);
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
        tvNewGoods.setOnClickListener(this);
        tvBrandsGoods.setOnClickListener(this);
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
        if (!TextUtils.isEmpty(SharePreferenceManager.getKeyCachedReUserid()))
        {
            App app = (App) getApplication();
            if (null != app.getReuserInfo())
            {
                ReuserInfo reuserInfo = app.getReuserInfo();
                //更新好友数据库
                SealUserInfoManager.getInstance().addFriend(new Friend(reuserInfo.getUser_id(),
                    getResources().getString(R.string.person_help), Uri.parse(reuserInfo.getAvatar()),
                    getResources().getString(R.string.person_help), null, null, null, null,
                    CharacterParser.getInstance().getSpelling(getResources().getString(R.string.person_help)),
                    TextUtils.isEmpty(getResources().getString(R.string.person_help)) ? null
                        : CharacterParser.getInstance().getSpelling(getResources().getString(R.string.person_help))));
            }
            RongIM.getInstance().setConversationToTop(Conversation.ConversationType.PRIVATE,
                    SharePreferenceManager.getKeyCachedReUserid(), true, new RongIMClient.ResultCallback<Boolean>()
                    {
                        @Override
                        public void onSuccess(Boolean aBoolean)
                        {
                            Logger.d(TAG, "setConversationToTop success, boolean: " + aBoolean);
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode)
                        {
                            Logger.d(TAG, "setConversationToTop error, errorCode: " + errorCode);
                        }
                    });
        }
    }

    private void getVersion()
    {
        SealUserInfoManager.getInstance().getAppVersion(new SealUserInfoManager.ResultCallback<VersionBean>()
        {
            @Override
            public void onSuccess(VersionBean versionBean)
            {
                Logger.d(TAG, "getVersion success!");
                checkVersion(versionBean);
            }

            @Override
            public void onError(String errString)
            {
                Logger.d(TAG, "getVersion fail!");
            }
        });
    }
    
    private void checkVersion(VersionBean bean)
    {
        if (null == bean)
        {
            Logger.d(TAG, "checkVersion, bean is null return!");
            return;
        }
        String version = bean.getVersion();
        final String downloadUrl = bean.getUpgrade();
        try
        {
            if (!TextUtils.isEmpty(downloadUrl) && !TextUtils.isEmpty(version) && !version.equals(getVersionName()))
            {
                Logger.d(TAG, "checkVersion, is new version!");
                if (HttpConstant.URL.equals(downloadUrl))
                {
                    Logger.d(TAG, "checkVersion, download url is only Base url!");
                    return;
                }
                DialogWithYesOrNoUtils.getInstance().showOnlyDialog(this,
                    getResources().getString(R.string.has_new_version_to_updata),
                    getResources().getString(R.string.updata_version_later),
                    getResources().getString(R.string.updata_version_rightnow),
                    new DialogWithYesOrNoUtils.DialogCallBack()
                    {
                        @Override
                        public void executeEvent()
                        {
                            Logger.d(TAG, "startService!");
                            Intent intent = new Intent(PhoneMainActivity.this, DownAPKService.class);
                            intent.putExtra("apk_url", downloadUrl);
                            PhoneMainActivity.this.startService(intent);
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
            }
        }
        catch (Exception e)
        {
            Logger.d(TAG, "checkVersion, exception: " + e);
        }

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
//            window.getDecorView()
//                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color4));
            params.width = ViewUtil.getIntance().getDisplayWidth(this);
            params.height = ViewUtil.getIntance().getStatusBarHeight(this);
            barView.setVisibility(View.GONE);
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

    //所有按钮状态初始化
    private void changeTextViewColor()
    {
        ivChat.setImageResource(R.mipmap.liaotian);
        tvChat.setTextColor(getResources().getColor(R.color.color3));
        ivBrands.setImageResource(R.mipmap.pinpai);
        tvBrands.setTextColor(getResources().getColor(R.color.color3));
        ivMy.setImageResource(R.mipmap.my);
        tvMy.setTextColor(getResources().getColor(R.color.color3));
        tvApp.setVisibility(View.GONE);
        tvAddress.setVisibility(View.GONE);
        tvCircle.setVisibility(View.GONE);
        tvNewGoods.setVisibility(View.GONE);
        tvBrandsGoods.setVisibility(View.GONE);
        tvAddress.setBackgroundResource(R.drawable.bg_left_white_empty);
        tvAddress.setTextColor(getResources().getColor(R.color.colorWhite));
        tvCircle.setBackgroundResource(R.drawable.bg_right_white_empty);
        tvCircle.setTextColor(getResources().getColor(R.color.colorWhite));
//        tvNewGoods.setBackgroundResource(R.drawable.bg_right_white_empty);
//        tvNewGoods.setTextColor(getResources().getColor(R.color.colorWhite));
//        tvBrandsGoods.setBackgroundResource(R.drawable.bg_left_white_full);
//        tvBrandsGoods.setTextColor(getResources().getColor(R.color.color4));
    }

    //是否显示聊天列表（必须作为左后一个false赋值）
    private void chatShowOrHide(boolean show)
    {
        if (show)
        {
            if (viewParent.getVisibility() == View.GONE)
            {
                viewParent.setVisibility(View.VISIBLE);
                switch (viewParent.getCurrentItem())
                {
                    case 0:
                        tvAddress.setVisibility(View.VISIBLE);
                        tvCircle.setVisibility(View.VISIBLE);
                        tvChat.setTextColor(getResources().getColor(R.color.color1));
                        ivChat.setImageResource(R.mipmap.liaotian_press);
                        break;
                    case 1:
                        tvNewGoods.setVisibility(View.VISIBLE);
                        tvBrandsGoods.setVisibility(View.VISIBLE);
                        break;
                    case 2:

                        break;
                    default:
                        break;
                }
            }
        }
        else
        {
            if (viewParent.getVisibility() == View.VISIBLE)
            {
                viewParent.setVisibility(View.GONE);
            }
        }
    }

    //是否显示通讯录
    private void contactShowOrHide(boolean show)
    {
        if (show)
        {
            if (frameLayout.getVisibility() == View.GONE)
            {
                FragmentManager fm = getSupportFragmentManager();
                if (null == contactsFragment)
                {
                    contactsFragment = new ContactsFragment();
                    fm.beginTransaction().add(R.id.frameLayout, contactsFragment).commit();
                }
                else
                {
                    fm.beginTransaction().show(contactsFragment).commit();
                }
                frameLayout.setVisibility(View.VISIBLE);
            }
            tvAddress.setVisibility(View.VISIBLE);
            tvCircle.setVisibility(View.VISIBLE);
            tvAddress.setBackgroundResource(R.drawable.bg_left_white_full);
            tvAddress.setTextColor(getResources().getColor(R.color.color4));
        }
        else
        {
            if (frameLayout.getVisibility() == View.VISIBLE)
            {
                if (null != contactsFragment)
                {
                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction().hide(contactsFragment).commit();
                }
                frameLayout.setVisibility(View.GONE);
            }
            tvAddress.setBackgroundResource(R.drawable.bg_left_white_empty);
            tvAddress.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void newGoodsShowOrHide(boolean show)
    {
        if (show)
        {
            tvBrandsGoods.setVisibility(View.VISIBLE);
            tvNewGoods.setVisibility(View.VISIBLE);
            tvNewGoods.setBackgroundResource(R.drawable.bg_right_white_full);
            tvNewGoods.setTextColor(getResources().getColor(R.color.color4));
            if (null != callBack && callBack.getCateType() != 1)
            {
                callBack.toLoad(1);
            }
        }
        else
        {
            tvNewGoods.setBackgroundResource(R.drawable.bg_right_white_empty);
            tvNewGoods.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void brandsGoodsShowOrHide(boolean show)
    {
        if (show)
        {
            tvBrandsGoods.setVisibility(View.VISIBLE);
            tvNewGoods.setVisibility(View.VISIBLE);
            tvBrandsGoods.setBackgroundResource(R.drawable.bg_left_white_full);
            tvBrandsGoods.setTextColor(getResources().getColor(R.color.color4));
            if (null != callBack && callBack.getCateType() != 0)
            {
                callBack.toLoad(0);
            }
        }
        else
        {
            tvBrandsGoods.setBackgroundResource(R.drawable.bg_left_white_empty);
            tvBrandsGoods.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void changeSelectedTabState(int position)
    {
        switch (position)
        {
            case 0:
                tvChat.setTextColor(getResources().getColor(R.color.color1));
                ivChat.setImageResource(R.mipmap.liaotian_press);
                tvAddress.setVisibility(View.VISIBLE);
                tvCircle.setVisibility(View.VISIBLE);
                break;
            case 1:
                tvBrands.setTextColor(getResources().getColor(R.color.color1));
                ivBrands.setImageResource(R.mipmap.pinpai_press);
                tvBrandsGoods.setVisibility(View.VISIBLE);
                tvNewGoods.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvApp.setVisibility(View.VISIBLE);
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
                if (viewParent.getCurrentItem() == 0)
                {
                    if (firstClick == 0)
                    {
                        firstClick = System.currentTimeMillis();
                    }
                    else
                    {
                        secondClick = System.currentTimeMillis();
                    }
                    Logger.i(TAG, "time = " + (secondClick - firstClick));
                    if (secondClick - firstClick > 0 && secondClick - firstClick <= 800)
                    {
                        mConversationListFragment.focusUnreadItem();
                        firstClick = 0;
                        secondClick = 0;
                    }
                    else if (firstClick != 0 && secondClick != 0)
                    {
                        firstClick = 0;
                        secondClick = 0;
                    }
                }
                viewParent.setCurrentItem(0, false);
                contactShowOrHide(false);
                brandsGoodsShowOrHide(false);
                newGoodsShowOrHide(false);
                chatShowOrHide(true);
                break;
            case R.id.ll_brands:
                viewParent.setCurrentItem(1, false);
                contactShowOrHide(false);
                newGoodsShowOrHide(false);
                chatShowOrHide(true);
                brandsGoodsShowOrHide(true);
                break;
            case R.id.ll_my:
                viewParent.setCurrentItem(2, false);
                contactShowOrHide(false);
                brandsGoodsShowOrHide(false);
                newGoodsShowOrHide(false);
                chatShowOrHide(true);
                break;
            case R.id.tv_book:
                changeTextViewColor();
                brandsGoodsShowOrHide(false);
                newGoodsShowOrHide(false);
                chatShowOrHide(false);
                contactShowOrHide(true);
                break;
            case R.id.tv_circle:

                break;
            case R.id.tv_new_goods:
                contactShowOrHide(false);
                brandsGoodsShowOrHide(false);
                chatShowOrHide(true);
                newGoodsShowOrHide(true);
                break;
            case R.id.tv_brands_goods:
                viewParent.setCurrentItem(1, false);
                contactShowOrHide(false);
                newGoodsShowOrHide(false);
                chatShowOrHide(true);
                brandsGoodsShowOrHide(true);
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
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra("kickedByOtherClient", false))
        {
            goToLogin();
        }
    }

    private void goToLogin()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy()
    {
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(this);
        super.onDestroy();
    }

    private String getVersionName() throws Exception
    {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
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

    public void setCallBack(BrandsOrNewGoodsCallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface BrandsOrNewGoodsCallBack
    {
        //新品1，品牌0
        void toLoad(int cateId);

        //新品1，品牌0
        int getCateType();
    }

}
