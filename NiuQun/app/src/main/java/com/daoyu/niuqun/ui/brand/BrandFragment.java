package com.daoyu.niuqun.ui.brand;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.chat.SealUserInfoManager.ResultCallback;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.BrandInfo;
import com.daoyu.niuqun.bean.BrandsData;
import com.daoyu.niuqun.ui.adapter.BrandsListAdapter;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.view.AutoLoadListener;
import com.daoyu.niuqun.view.AutoLoadListener.AutoLoadCallBack;

public class BrandFragment extends Fragment implements OnRefreshListener
{

    private static final String TAG = "BrandFragment";

    private Toast mToast;

    private GridView gridView;

    private View footView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<BrandInfo> brandInfos = new ArrayList<>();

    private BrandsListAdapter brandsListAdapter;

    //加载是否完成，默认已完成
    private boolean finishLoad = true;

    //数据是否已全部显示
    private boolean isAll = false;

    //下一次加载的页码
    private int page = 1;

    //新品1，品牌0
    protected int cateType = 0;

    private AutoLoadCallBack callBack = new AutoLoadCallBack()
    {
        @Override
        public void execute()
        {
            if (isAll)
            {
                showToast(getActivity().getResources().getString(R.string.has_load_all));
                return;
            }
            if (finishLoad)
            {
                footView.setVisibility(View.VISIBLE);
                getBrandsList(page + 1);
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View mainView = inflater.inflate(R.layout.fragment_brand, container, false);
        swipeRefreshLayout = mainView.findViewById(R.id.sl_ly);
        gridView = mainView.findViewById(R.id.gridView);
        footView = mainView.findViewById(R.id.footview);
        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_red_light,
            android.R.color.holo_orange_light, android.R.color.holo_blue_light);
        brandsListAdapter = new BrandsListAdapter(getActivity(), brandInfos);
        gridView.setAdapter(brandsListAdapter);
        AutoLoadListener autoLoadListener = new AutoLoadListener(callBack);
        gridView.setOnScrollListener(autoLoadListener);
        getBrandsList(1);
    }

    @Override
    public void onRefresh()
    {
        swipeRefreshLayout.setRefreshing(false);
        if (finishLoad)
        {
            getBrandsList(1);
        }
    }

    private void getBrandsList(int nextpage)
    {
        finishLoad = false;
        SealUserInfoManager.getInstance().getBrandsList(cateType, nextpage, new ResultCallback<BrandsData>()
        {
            @Override
            public void onSuccess(BrandsData brandsData)
            {
                Logger.d(TAG, "getBrandsList successful!");
                updataList(brandsData);
                loadFinish();
            }

            @Override
            public void onError(String errString)
            {
                Logger.d(TAG, "getBrandsList, error: " + errString);
                loadFinish();
            }
        });
    }

    private void loadFinish()
    {
        finishLoad = true;
        footView.setVisibility(View.GONE);
    }

    private void updataList(BrandsData brandsData)
    {
        if (null == brandsData)
        {
            Logger.d(TAG, "updataList, brandsData is null, return!");
            return;
        }
        page = brandsData.getNowpage();
        if (page == brandsData.getCountpage())
        {
            isAll = true;
        }
        if (1 == page)
        {
            brandsListAdapter.clear();
        }
        if (null != brandsData.getList())
        {
            brandsListAdapter.addAllItem(brandsData.getList());
        }
        brandsListAdapter.notifyDataSetChanged();
    }

    protected void showToast(String msg)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        else
        {
            mToast.setText(msg);
        }
        mToast.show();
    }

}
