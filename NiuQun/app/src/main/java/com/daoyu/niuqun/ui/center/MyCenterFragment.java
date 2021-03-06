package com.daoyu.niuqun.ui.center;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.chat.SealUserInfoManager.ResultCallback;
import com.daoyu.chat.server.widget.SelectableRoundedImageView;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.ClothesItem;
import com.daoyu.niuqun.bean.CollectionItem;
import com.daoyu.niuqun.bean.DrinkItem;
import com.daoyu.niuqun.bean.EatItem;
import com.daoyu.niuqun.bean.EducationItem;
import com.daoyu.niuqun.bean.FinancialItem;
import com.daoyu.niuqun.bean.HappyItem;
import com.daoyu.niuqun.bean.LiveItem;
import com.daoyu.niuqun.bean.MedicalItem;
import com.daoyu.niuqun.bean.MyBaseItem;
import com.daoyu.niuqun.bean.MySelfBean;
import com.daoyu.niuqun.bean.OrdersItem;
import com.daoyu.niuqun.bean.PaymentItem;
import com.daoyu.niuqun.bean.PhotosItem;
import com.daoyu.niuqun.bean.PlayItem;
import com.daoyu.niuqun.bean.SettingItem;
import com.daoyu.niuqun.bean.ShoppingCartItem;
import com.daoyu.niuqun.bean.SymbolItem;
import com.daoyu.niuqun.bean.TravelItem;
import com.daoyu.niuqun.constant.ActivityResultConstant;
import com.daoyu.niuqun.ui.App;
import com.daoyu.niuqun.ui.adapter.MyItemListAdapter;
import com.daoyu.niuqun.util.EventManager;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.SharePreferenceManager;
import com.daoyu.niuqun.view.MyGridView;

import io.rong.eventbus.EventBus;
import io.rong.imageloader.core.ImageLoader;

public class MyCenterFragment extends Fragment
{

    private static final String TAG = "MyCenterFragment";

    private MyGridView gridWallet;

    private MyGridView gridApp;

    private MyGridView gridOther;

    private SelectableRoundedImageView iv_avatar;

    private TextView tv_nickname;

    private TextView tv_number;

    private LinearLayout ll_info;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View mainView = inflater.inflate(R.layout.fragment_my_center, container, false);
        initView(mainView);
        initData();
        ScrollView scrollView = mainView.findViewById(R.id.parentView);
        scrollView.setFocusable(true);
        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        updateUI();
        EventBus.getDefault().register(this);
    }

    private void initView(View view)
    {
        gridWallet = view.findViewById(R.id.grid_wallet);
        gridApp = view.findViewById(R.id.grid_app);
        gridOther = view.findViewById(R.id.grid_others);
        iv_avatar = view.findViewById(R.id.iv_avatar);
        ll_info = view.findViewById(R.id.ll_info);
        tv_nickname = view.findViewById(R.id.tv_nickname);
        tv_number = view.findViewById(R.id.tv_number);
    }

    private void initData()
    {
        List<MyBaseItem> wallets = new ArrayList<>();
        wallets.add(new PaymentItem(getActivity()));
        wallets.add(new SymbolItem(getActivity()));
        wallets.add(new FinancialItem(getActivity()));
        MyItemListAdapter walletAdapter = new MyItemListAdapter(getActivity(), wallets);
        gridWallet.setAdapter(walletAdapter);

        List<MyBaseItem> apps = new ArrayList<>();
        apps.add(new CollectionItem(getActivity()));
        apps.add(new ShoppingCartItem(getActivity()));
        apps.add(new OrdersItem(getActivity()));
        apps.add(new PhotosItem(getActivity()));
        apps.add(new SettingItem(getActivity()));
        apps.add(new MyBaseItem());
        MyItemListAdapter appAdapter = new MyItemListAdapter(getActivity(), apps);
        gridApp.setAdapter(appAdapter);

        List<MyBaseItem> others = new ArrayList<>();
        others.add(new EatItem(getActivity()));
        others.add(new DrinkItem(getActivity()));
        others.add(new PlayItem(getActivity()));
        others.add(new HappyItem(getActivity()));
        others.add(new ClothesItem(getActivity()));
        others.add(new LiveItem(getActivity()));
        others.add(new TravelItem(getActivity()));
        others.add(new EducationItem(getActivity()));
        others.add(new MedicalItem(getActivity()));
        MyItemListAdapter otherAdapter = new MyItemListAdapter(getActivity(), others);
        gridOther.setAdapter(otherAdapter);

        ImageLoader.getInstance().displayImage(SharePreferenceManager.getCachedAvatarPath(), iv_avatar, App.getOptions());
        tv_nickname.setText(SharePreferenceManager.getCachedUsername());
        String number = SharePreferenceManager.getCacheHerdNo();
        if (TextUtils.isEmpty(number))
        {
            number = SharePreferenceManager.getCacheMobile();
        }
        String num = getActivity().getResources().getString(R.string.niuqun_number_hint) + number;
        tv_number.setText(num);
        ll_info.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Logger.d(TAG, "go to Info!");
                Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUI()
    {
        SealUserInfoManager.getInstance().getMyCenter(new ResultCallback<MySelfBean>()
        {
            @Override
            public void onSuccess(MySelfBean bean)
            {
                Logger.d(TAG, "updateUI successful!");
                updataInfo(bean);
            }

            @Override
            public void onError(String errString)
            {
                Logger.d(TAG, "updateUI, error: " + errString);
            }
        });
    }

    private void updataInfo(MySelfBean bean)
    {
        if (null != bean)
        {
            Logger.d(TAG, "updataInfo, bean has value!");
            if (!TextUtils.isEmpty(bean.getAvatar()))
            {
                ImageLoader.getInstance().displayImage(SharePreferenceManager.getCachedAvatarPath(), iv_avatar, App.getOptions());
            }
            if (!TextUtils.isEmpty(bean.getUser_name()))
            {
                tv_nickname.setText(bean.getUser_name());
            }
            if (!TextUtils.isEmpty(bean.getHerdno()))
            {
                SharePreferenceManager.setCacheHerdNo(bean.getHerdno());
                String num = getActivity().getResources().getString(R.string.niuqun_number_hint) + bean.getHerdno();
                tv_number.setText(num);
            }
        }
    }

    public void onEventMainThread(EventManager.PersonInfoSuccess event)
    {
        Logger.d(TAG, "onEventMainThread, PersonInfoSuccess!");
        if (ActivityResultConstant.UPDATA_USERNAME == event.getType())
        {
            tv_nickname.setText(SharePreferenceManager.getCachedUsername());
        }
        else if (ActivityResultConstant.UPDATA_NIUQUN_NUMBER == event.getType())
        {
            String number = SharePreferenceManager.getCacheHerdNo();
            if (TextUtils.isEmpty(number))
            {
                number = SharePreferenceManager.getCacheMobile();
            }
            number = getActivity().getResources().getString(R.string.niuqun_number_hint) + number;
            tv_number.setText(number);
        }
        else if (ActivityResultConstant.UPDATA_AVATAR == event.getType())
        {
            ImageLoader.getInstance().displayImage(SharePreferenceManager.getCachedAvatarPath(), iv_avatar, App.getOptions());
        }
        else
        {
            tv_nickname.setText(SharePreferenceManager.getCachedUsername());
            String number = SharePreferenceManager.getCacheHerdNo();
            if (TextUtils.isEmpty(number))
            {
                number = SharePreferenceManager.getCacheMobile();
            }
            number = getActivity().getResources().getString(R.string.niuqun_number_hint) + number;
            tv_number.setText(number);
        }
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
