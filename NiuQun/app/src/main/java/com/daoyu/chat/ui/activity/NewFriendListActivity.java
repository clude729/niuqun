package com.daoyu.chat.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.daoyu.niuqun.R;
import com.daoyu.chat.SealAppContext;
import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.chat.db.Friend;
import com.daoyu.chat.server.broadcast.BroadcastManager;
import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.pinyin.CharacterParser;
import com.daoyu.chat.server.response.AgreeFriendsResponse;
import com.daoyu.chat.server.response.UserRelationshipResponse;
import com.daoyu.chat.server.utils.CommonUtils;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.adapter.NewFriendListAdapter;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.util.Logger;

public class NewFriendListActivity extends BaseActivity
    implements NewFriendListAdapter.OnItemButtonClick, OnClickListener
{

    private static final String TAG = "NewFriendListActivity";

    private static final int GET_ALL = 11;

    private static final int AGREE_FRIENDS = 12;

    public static final int FRIEND_LIST_REQUEST_CODE = 1001;

    private ListView shipListView;

    private NewFriendListAdapter adapter;

    private String friendId;

    private TextView isData;

    private UserRelationshipResponse userRelationshipResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friendlist);
        initView();
        if (!CommonUtils.isNetworkConnected(mContext))
        {
            NToast.shortToast(mContext, R.string.check_network);
            return;
        }
        LoadDialog.show(mContext);
        request(GET_ALL);
        adapter = new NewFriendListAdapter(mContext);
        shipListView.setAdapter(adapter);
    }

    protected void initView()
    {
        setTitle(R.string.new_friends);
        shipListView = (ListView) findViewById(R.id.shiplistview);
        isData = (TextView) findViewById(R.id.isData);
        Button rightButton = getHeadRightButton();
        rightButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.de_address_new_friend));
        rightButton.setOnClickListener(this);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case GET_ALL:
                return action.getAllUserRelationship();
            case AGREE_FRIENDS:
                return action.agreeFriends(friendId);
        }
        return super.doInBackground(requestCode, id);
    }

    @Override
    public void onSuccess(int requestCode, Object result)
    {
        if (result != null)
        {
            switch (requestCode)
            {
                case GET_ALL:
                    userRelationshipResponse = (UserRelationshipResponse) result;

                    if (null == userRelationshipResponse.getResult()
                        || userRelationshipResponse.getResult().size() == 0)
                    {
                        isData.setVisibility(View.VISIBLE);
                        LoadDialog.dismiss(mContext);
                        return;
                    }
                    // TODO: 暂时没有添加时间，服务器返回数据已排好序，无需再次排序
                    //                    Collections.sort(userRelationshipResponse.getResult(), new Comparator<UserRelationshipResponse.ResultEntity>() {
                    //
                    //                        @Override
                    //                        public int compare(UserRelationshipResponse.ResultEntity lhs, UserRelationshipResponse.ResultEntity rhs) {
                    //                            Date date1 = stringToDate(lhs);
                    //                            Date date2 = stringToDate(rhs);
                    //                            if (date1.before(date2)) {
                    //                                return 1;
                    //                            }
                    //                            return -1;
                    //                        }
                    //                    });

                    adapter.removeAll();
                    adapter.addData(userRelationshipResponse.getResult());
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemButtonClick(this);
                    LoadDialog.dismiss(mContext);
                    break;
                case AGREE_FRIENDS:
                    AgreeFriendsResponse afres = (AgreeFriendsResponse) result;
                    if (HttpConstant.SUCCESS.equals(afres.getCode()))
                    {
                        UserRelationshipResponse.ResultEntity bean = userRelationshipResponse.getResult().get(index);
                        SealUserInfoManager.getInstance()
                            .addFriend(new Friend(bean.getUser().getId(), bean.getUser().getNickname(),
                                Uri.parse(bean.getUser().getPortraitUri()), bean.getDisplayName(), String.valueOf(1),
                                null, null, null,
                                CharacterParser.getInstance().getSpelling(bean.getUser().getNickname()),
                                CharacterParser.getInstance().getSpelling(bean.getDisplayName())));
                        // 通知好友列表刷新数据
                        NToast.shortToast(mContext, R.string.agreed_friend);
                        LoadDialog.dismiss(mContext);
                        BroadcastManager.getInstance(mContext).sendBroadcast(SealAppContext.UPDATE_FRIEND);
                        request(GET_ALL); //刷新 UI 按钮
                    }

            }
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result)
    {
        switch (requestCode)
        {
            case GET_ALL:
                break;

        }
    }

    @Override
    protected void onDestroy()
    {
        if (adapter != null)
        {
            adapter = null;
        }
        super.onDestroy();
    }

    private int index;

    @Override
    public boolean onButtonClick(int position, View view, int status)
    {
        index = position;
        Logger.d(TAG, "onButtonClick, position: " + position + " ,status: " + status);
        switch (status)
        {
            case 11: //收到了好友邀请
                if (!CommonUtils.isNetworkConnected(mContext))
                {
                    NToast.shortToast(mContext, R.string.check_network);
                    break;
                }
                LoadDialog.show(mContext);
                friendId = userRelationshipResponse.getResult().get(position).getUser().getId();
                request(AGREE_FRIENDS);
                break;
            case 0: // 发出了好友邀请
                break;
            case 2: // 忽略好友邀请
                break;
            case 1: // 已是好友
                break;
            case 3: // 已拒绝
                break;
            case 4: // 删除了好友关系
                break;
        }
        return false;
    }

    //    private Date stringToDate(UserRelationshipResponse.ResultEntity resultEntity) {
    //        String updatedAt = resultEntity.getUpdatedAt();
    //        String updatedAtDateStr = updatedAt.substring(0, 10) + " " + updatedAt.substring(11, 16);
    //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    //        Date updateAtDate = null;
    //        try {
    //            updateAtDate = simpleDateFormat.parse(updatedAtDateStr);
    //        } catch (ParseException e) {
    //            e.printStackTrace();
    //        }
    //        return updateAtDate;
    //    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(NewFriendListActivity.this, SearchFriendActivity.class);
        startActivityForResult(intent, FRIEND_LIST_REQUEST_CODE);
    }
}
