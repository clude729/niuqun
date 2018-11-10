package com.daoyu.niuqun.ui.center;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.chat.SealUserInfoManager.ResultCallback;
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
import com.daoyu.niuqun.ui.adapter.MyItemListAdapter;
import com.daoyu.niuqun.util.ImageLoad;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.SharePreferenceManager;
import com.daoyu.niuqun.view.MyGridView;

public class MyCenterFragment extends Fragment
{

    private static final String TAG = "MyCenterFragment";

    private MyGridView gridWallet;

    private MyGridView gridApp;

    private MyGridView gridOther;

    private ImageView iv_avatar;

    private TextView tv_nickname;

    private TextView tv_number;

    private ImageView iv_info;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View mainView = inflater.inflate(R.layout.fragment_my_center, container, false);
        initView(mainView);
        initData();
        updateUI();
        ScrollView scrollView = mainView.findViewById(R.id.parentView);
        scrollView.setFocusable(true);
        return mainView;
    }

    private void initView(View view)
    {
        gridWallet = view.findViewById(R.id.grid_wallet);
        gridApp = view.findViewById(R.id.grid_app);
        gridOther = view.findViewById(R.id.grid_others);
        iv_avatar = view.findViewById(R.id.iv_avatar);
        iv_info = view.findViewById(R.id.iv_info);
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

        ImageLoad.getInstance().load(getActivity(), iv_avatar, SharePreferenceManager.getCachedAvatarPath(),
            new RequestOptions().error(R.drawable.default_useravatar).placeholder(R.drawable.default_useravatar));
        tv_nickname.setText(SharePreferenceManager.getCachedUsername());
        tv_number.setText(SharePreferenceManager.getCacheMobile());
        iv_info.setOnClickListener(new View.OnClickListener()
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
                ImageLoad.getInstance().load(getActivity(), iv_avatar, bean.getAvatar(),
                    new RequestOptions().error(R.mipmap.default__avatar).placeholder(R.mipmap.default__avatar));
            }
            if (!TextUtils.isEmpty(bean.getUser_name()))
            {
                tv_nickname.setText(bean.getUser_name());
            }
            if (!TextUtils.isEmpty(bean.getHerdno()))
            {
                tv_number.setText(bean.getHerdno());
            }
        }
    }

}
