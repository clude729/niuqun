package com.daoyu.chat.ui.activity;

import android.content.Context;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.ui.App;
import com.daoyu.niuqun.R;
import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.chat.SealAppContext;
import com.daoyu.chat.db.Friend;
import com.daoyu.chat.server.network.async.AsyncTaskManager;
import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.FriendInvitationResponse;
import com.daoyu.chat.server.response.GetUserInfoByPhoneResponse;
import com.daoyu.chat.server.utils.AMUtils;
import com.daoyu.chat.server.utils.CommonUtils;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.DialogWithYesOrNoUtils;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.server.widget.SelectableRoundedImageView;
import com.daoyu.niuqun.util.SharePreferenceManager;

import io.rong.imageloader.core.ImageLoader;
import io.rong.imlib.model.UserInfo;

public class SearchFriendActivity extends BaseActivity
{

    private static final int CLICK_CONVERSATION_USER_PORTRAIT = 1;

    private static final int SEARCH_PHONE = 10;

    private static final int ADD_FRIEND = 11;

    private EditText mEtSearch;

    private LinearLayout searchItem;

    private TextView searchName;

    private SelectableRoundedImageView searchImage;

    private String mPhone;

    private String addFriendMessage;

    private String mFriendId;

    private Friend mFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__chat_search);
        setTitle((R.string.search_friend));

        mEtSearch = findViewById(R.id.search_edit);
        searchItem = findViewById(R.id.search_result);
        searchName = findViewById(R.id.search_name);
        searchImage = findViewById(R.id.search_header);

        mEtSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() == 11)
                {
                    mPhone = s.toString().trim();
                    if (!AMUtils.isMobile(mPhone))
                    {
                        NToast.shortToast(mContext, "非法手机号");
                        return;
                    }
                    hintKbTwo();
                    LoadDialog.show(mContext);
                    request(SEARCH_PHONE, true);
                }
                else
                {
                    searchItem.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case SEARCH_PHONE:
                return action.getUserInfoFromPhone("86", mPhone);
            case ADD_FRIEND:
                return action.sendFriendInvitation(mFriendId, addFriendMessage);
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
                case SEARCH_PHONE:
                    final GetUserInfoByPhoneResponse userInfoByPhoneResponse = (GetUserInfoByPhoneResponse) result;
                    if (HttpConstant.SUCCESS.equals(userInfoByPhoneResponse.getCode()))
                    {
                        LoadDialog.dismiss(mContext);
                        NToast.shortToast(mContext, "success");
                        mFriendId = userInfoByPhoneResponse.getResult().getUser_id();
                        searchItem.setVisibility(View.VISIBLE);
                        String portraitUri = null;
                        if (userInfoByPhoneResponse.getResult() != null)
                        {
                            GetUserInfoByPhoneResponse.ResultEntity userInfoByPhoneResponseResult = userInfoByPhoneResponse
                                .getResult();
                            UserInfo userInfo = new UserInfo(userInfoByPhoneResponseResult.getUser_id(),
                                userInfoByPhoneResponseResult.getUser_name(),
                                Uri.parse(userInfoByPhoneResponseResult.getAvatar()));
                            portraitUri = SealUserInfoManager.getInstance().getPortraitUri(userInfo);
                        }
                        ImageLoader.getInstance().displayImage(portraitUri, searchImage, App.getOptions());
                        searchName.setText(userInfoByPhoneResponse.getResult().getUser_name());
                        searchItem.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                if (isFriendOrSelf(mFriendId))
                                {
                                    Intent intent = new Intent(SearchFriendActivity.this, UserDetailActivity.class);
                                    intent.putExtra("friend", mFriend);
                                    intent.putExtra("type", CLICK_CONVERSATION_USER_PORTRAIT);
                                    startActivity(intent);
                                    SealAppContext.getInstance().pushActivity(SearchFriendActivity.this);
                                    return;
                                }
                                DialogWithYesOrNoUtils.getInstance().showEditDialog(mContext,
                                    getString(R.string.add_text), getString(R.string.add_friend),
                                    new DialogWithYesOrNoUtils.DialogCallBack()
                                    {
                                        @Override
                                        public void executeEvent()
                                        {

                                        }

                                        @Override
                                        public void updatePassword(String oldPassword, String newPassword)
                                        {

                                        }

                                        @Override
                                        public void executeEditEvent(String editText)
                                        {
                                            if (!CommonUtils.isNetworkConnected(mContext))
                                            {
                                                NToast.shortToast(mContext, R.string.network_not_available);
                                                return;
                                            }
                                            addFriendMessage = editText;
                                            if (TextUtils.isEmpty(editText))
                                            {
                                                addFriendMessage = "我是" + SharePreferenceManager.getCachedUsername();
                                            }
                                            if (!TextUtils.isEmpty(mFriendId))
                                            {
                                                LoadDialog.show(mContext);
                                                request(ADD_FRIEND);
                                            }
                                            else
                                            {
                                                NToast.shortToast(mContext, "id is null");
                                            }
                                        }
                                    });
                            }
                        });

                    }
                    break;
                case ADD_FRIEND:
                    FriendInvitationResponse fres = (FriendInvitationResponse) result;
                    if (HttpConstant.SUCCESS.equals(fres.getCode()))
                    {
                        NToast.shortToast(mContext, getString(R.string.request_success));
                        LoadDialog.dismiss(mContext);
                    }
                    else
                    {
                        NToast.shortToast(mContext, "请求失败, " + fres.getMessage());
                        LoadDialog.dismiss(mContext);
                    }
                    break;
            }
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result)
    {
        switch (requestCode)
        {
            case ADD_FRIEND:
                if (result instanceof FriendInvitationResponse)
                {
                    NToast.shortToast(mContext, ((FriendInvitationResponse) result).getMessage());
                }
                LoadDialog.dismiss(mContext);
                break;
            case SEARCH_PHONE:
                if (state == AsyncTaskManager.HTTP_ERROR_CODE || state == AsyncTaskManager.HTTP_NULL_CODE)
                {
                    super.onFailure(requestCode, state, result);
                }
                else
                {
                    NToast.shortToast(mContext, "用户不存在");
                }
                LoadDialog.dismiss(mContext);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        hintKbTwo();
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void hintKbTwo()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm && imm.isActive() && getCurrentFocus() != null)
        {
            if (getCurrentFocus().getWindowToken() != null)
            {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private boolean isFriendOrSelf(String id)
    {
        String inputPhoneNumber = mEtSearch.getText().toString().trim();
        String selfPhoneNumber = SharePreferenceManager.getCacheMobile();
        if (!TextUtils.isEmpty(selfPhoneNumber))
        {
            if (inputPhoneNumber.equals(selfPhoneNumber))
            {
                mFriend = new Friend(SharePreferenceManager.getKeyCachedUserid(),
                    SharePreferenceManager.getCachedUsername(),
                    Uri.parse(SharePreferenceManager.getCachedAvatarPath()));
                return true;
            }
            else
            {
                mFriend = SealUserInfoManager.getInstance().getFriendByID(id);
                if (mFriend != null)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
