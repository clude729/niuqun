package com.daoyu.niuqun.ui.center;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.daoyu.chat.SealConst;
import com.daoyu.chat.server.broadcast.BroadcastManager;
import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.SetPortraitResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.utils.photo.PhotoUtils;
import com.daoyu.chat.server.widget.BottomMenuDialog;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.server.widget.SelectableRoundedImageView;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.constant.ActivityResultConstant;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.util.EventManager;
import com.daoyu.niuqun.util.ImageLoad;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.SharePreferenceManager;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * 个人信息
 */
public class PersonInfoActivity extends BaseActivity implements OnClickListener
{

    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;

    private static final String TAG = "PersonInfoActivity";

    private SelectableRoundedImageView imageAvatar;

    private TextView tvName;

    private TextView tvNumber;

    private LinearLayout llAvatar;

    private LinearLayout llName;

    private LinearLayout llNumber;

    private LinearLayout llCode;

    private LinearLayout llSign;

    private LinearLayout llAddress;

    private PhotoUtils photoUtils;

    private BottomMenuDialog dialog;

    private Uri selectUri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        initView();
        initListenter();
        initData();
        EventBus.getDefault().register(this);
    }

    private void initView()
    {
        setTitle(R.string.person_info);
        imageAvatar = findViewById(R.id.iv_avatar);
        tvName = findViewById(R.id.tv_name);
        tvNumber = findViewById(R.id.tv_number);
        llAddress = findViewById(R.id.ll_address);
        llAvatar = findViewById(R.id.ll_avatar);
        llCode = findViewById(R.id.ll_code);
        llName = findViewById(R.id.ll_name);
        llNumber = findViewById(R.id.ll_number);
        llSign = findViewById(R.id.ll_sign);
    }

    private void initListenter()
    {
        llAvatar.setOnClickListener(this);
        llName.setOnClickListener(this);
        llNumber.setOnClickListener(this);
        llCode.setOnClickListener(this);
        llSign.setOnClickListener(this);
        llAddress.setOnClickListener(this);
    }

    private void initData()
    {
        ImageLoad.getInstance().load(this, imageAvatar, SharePreferenceManager.getCachedAvatarPath(),
            new RequestOptions().error(R.drawable.default_useravatar).placeholder(R.drawable.default_useravatar));
        tvName.setText(SharePreferenceManager.getCachedUsername());
        String number = SharePreferenceManager.getCacheHerdNo();
        if (TextUtils.isEmpty(number))
        {
            number = SharePreferenceManager.getCacheMobile();
        }
        tvNumber.setText(number);
        setPortraitChangeListener();
    }

    private void setPortraitChangeListener()
    {
        photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener()
        {
            @Override
            public void onPhotoResult(Uri uri)
            {
                if (uri != null && !TextUtils.isEmpty(uri.getPath()))
                {
                    selectUri = uri;
                    LoadDialog.show(mContext);
                    request(ResponseConstant.UPDATA_AVATAR, true);
                }
            }

            @Override
            public void onPhotoCancel()
            {

            }
        });
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.UPDATA_AVATAR:
                return action.setPortrait(selectUri.getPath());
            default:
                break;
        }
        return super.doInBackground(requestCode, id);
    }

    @Override
    public void onSuccess(int requestCode, Object result)
    {
        if (null != result)
        {
            switch (requestCode)
            {
                case ResponseConstant.UPDATA_AVATAR:
                    if (result instanceof SetPortraitResponse)
                    {
                        SetPortraitResponse response = (SetPortraitResponse) result;
                        if (HttpConstant.SUCCESS.equals(response.getCode()))
                        {
                            Logger.d(TAG, "onSuccess, avatar!");
                            String avaUrl = response.getData();
                            SharePreferenceManager.setCachedAvatarPath(avaUrl);
                            if (null != RongIM.getInstance())
                            {
                                RongIM.getInstance()
                                    .setCurrentUserInfo(new UserInfo(SharePreferenceManager.getKeyCachedUserid(),
                                        SharePreferenceManager.getCachedUsername(), Uri.parse(avaUrl)));
                            }
                            ImageLoad.getInstance().load(PersonInfoActivity.this, imageAvatar, avaUrl,
                                new RequestOptions().error(R.drawable.default_useravatar)
                                    .placeholder(R.drawable.default_useravatar));
                            BroadcastManager.getInstance(mContext).sendBroadcast(SealConst.CHANGEINFO);
                        }
                    }
                    LoadDialog.dismiss(mContext);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result)
    {
        switch (requestCode)
        {
            case ResponseConstant.UPDATA_AVATAR:
                NToast.shortToast(mContext, "设置头像请求失败");
                LoadDialog.dismiss(mContext);
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
            case R.id.ll_name:
                goToTextEdit(TextEditActivity.NICKNAME);
                break;
            case R.id.ll_number:
                goToTextEdit(TextEditActivity.APP_NUMBER);
                break;
            case R.id.ll_code:

                break;
            case R.id.ll_sign:
                goToTextEdit(TextEditActivity.SIGN);
                break;
            case R.id.ll_address:
                goToAddressList();
                break;
            case R.id.ll_avatar:
                showPhotoDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case PhotoUtils.INTENT_CROP:
            case PhotoUtils.INTENT_TAKE:
            case PhotoUtils.INTENT_SELECT:
                photoUtils.onActivityResult(PersonInfoActivity.this, requestCode, resultCode, data);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void goToTextEdit(int type)
    {
        Intent intent = new Intent(this, TextEditActivity.class);
        intent.putExtra(IntentConstant.UPDATA_TYPE, type);
        startActivity(intent);
    }

    private void goToAddressList()
    {
        Intent intent = new Intent(this, AddressListActivity.class);
        intent.putExtra(IntentConstant.ADDRESS_FROM, 0);
        startActivity(intent);
    }

    /**
     * 弹出底部框
     */
    private void showPhotoDialog()
    {
        if (dialog != null && dialog.isShowing())
        {
            dialog.dismiss();
        }

        dialog = new BottomMenuDialog(mContext);
        dialog.setConfirmListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (dialog != null && dialog.isShowing())
                {
                    dialog.dismiss();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    int checkPermission = checkSelfPermission(Manifest.permission.CAMERA);
                    if (checkPermission != PackageManager.PERMISSION_GRANTED)
                    {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                        {
                            requestPermissions(new String[] {Manifest.permission.CAMERA },
                                REQUEST_CODE_ASK_PERMISSIONS);
                        }
                        else
                        {
                            new AlertDialog.Builder(mContext).setMessage("您需要在设置里打开相机权限。")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                        {
                                            requestPermissions(new String[] {Manifest.permission.CAMERA },
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                        }
                                    }
                                }).setNegativeButton("取消", null).create().show();
                        }
                        return;
                    }
                }
                photoUtils.takePicture(PersonInfoActivity.this);
            }
        });
        dialog.setMiddleListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (dialog != null && dialog.isShowing())
                {
                    dialog.dismiss();
                }
                photoUtils.selectPicture(PersonInfoActivity.this);
            }
        });
        dialog.show();
    }

    public void onEventMainThread(EventManager.PersonInfoSuccess event)
    {
        Logger.d(TAG, "onEventMainThread, PersonInfoSuccess!");
        if (ActivityResultConstant.UPDATA_USERNAME == event.getType())
        {
            tvName.setText(SharePreferenceManager.getCachedUsername());
        }
        else if (ActivityResultConstant.UPDATA_NIUQUN_NUMBER == event.getType())
        {
            String number = SharePreferenceManager.getCacheHerdNo();
            if (TextUtils.isEmpty(number))
            {
                number = SharePreferenceManager.getCacheMobile();
            }
            tvNumber.setText(number);
        }
        else
        {
            tvName.setText(SharePreferenceManager.getCachedUsername());
            String number = SharePreferenceManager.getCacheHerdNo();
            if (TextUtils.isEmpty(number))
            {
                number = SharePreferenceManager.getCacheMobile();
            }
            tvNumber.setText(number);
        }
    }

}
