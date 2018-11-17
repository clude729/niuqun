package com.daoyu.chat.server.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daoyu.niuqun.R;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.niuqun.util.SharePreferenceManager;

/**
 * Created by AMing on 15/11/26. Company RongCloud
 */
public class DialogWithYesOrNoUtils
{

    private static DialogWithYesOrNoUtils instance = null;

    public static DialogWithYesOrNoUtils getInstance()
    {
        if (instance == null)
        {
            instance = new DialogWithYesOrNoUtils();
        }
        return instance;
    }

    private DialogWithYesOrNoUtils()
    {
    }

    public void showOneButtonDialog(Context context, String titleInfo, String btnStr,
        final DialogWithYesOrNoUtils.DialogCallBack callBack)
    {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(context);
        alterDialog.setMessage(titleInfo);
        alterDialog.setCancelable(true);
        String str = context.getString(R.string.ac_select_friend_sure);
        if (!TextUtils.isEmpty(btnStr))
        {
            str = btnStr;
        }
        alterDialog.setPositiveButton(str, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (null != callBack)
                {
                    callBack.executeEvent();
                }
                else
                {
                    dialog.cancel();
                }
            }
        });
        alterDialog.show();
    }

    public void showDialog(Context context, String titleInfo, final DialogWithYesOrNoUtils.DialogCallBack callBack)
    {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(context);
        alterDialog.setMessage(titleInfo);
        alterDialog.setCancelable(true);

        alterDialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                callBack.executeEvent();
            }
        });
        alterDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        alterDialog.show();
    }

    public void showOnlyDialog(Context context, String titleInfo, String cancel, String ok, final DialogWithYesOrNoUtils.DialogCallBack callBack)
    {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_small_layout, null);
        alterDialog.setView(layout);
        TextView tvTitle = layout.findViewById(R.id.text_title);
        TextView tvCancel = layout.findViewById(R.id.tv_cancel);
        TextView tvOk = layout.findViewById(R.id.tv_ok);
        if (!TextUtils.isEmpty(titleInfo))
        {
            tvTitle.setText(titleInfo);
        }
        if (!TextUtils.isEmpty(cancel))
        {
            tvCancel.setText(cancel);
        }
        if (!TextUtils.isEmpty(ok))
        {
            tvOk.setText(ok);
        }
        final AlertDialog dialog = alterDialog.create();
        tvCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                callBack.executeEvent();
            }
        });
        dialog.show();
    }

    public interface DialogCallBack
    {
        void executeEvent();

        void executeEditEvent(String editText);

        void updatePassword(String oldPassword, String newPassword);
    }

    public void showEditDialog(Context context, String hintText, String OKText,
        final DialogWithYesOrNoUtils.DialogCallBack callBack)
    {
        final EditText et_search;
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_view, null);
        dialog.setView(layout);
        et_search = (EditText) layout.findViewById(R.id.searchC);
        et_search.setHint(hintText);
        dialog.setPositiveButton(OKText, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                String s = et_search.getText().toString().trim();
                callBack.executeEditEvent(s);
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {

            }

        });
        dialog.show();
    }

    public void showUpdatePasswordDialog(final Context context, final DialogWithYesOrNoUtils.DialogCallBack callBack)
    {
        final EditText oldPasswordEdit, newPasswrodEdit, newPassword2Edit;
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialogchangeview, null);
        dialog.setView(layout);
        oldPasswordEdit = (EditText) layout.findViewById(R.id.old_password);
        newPasswrodEdit = (EditText) layout.findViewById(R.id.new_password);
        newPassword2Edit = (EditText) layout.findViewById(R.id.new_password2);
        dialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                String old = oldPasswordEdit.getText().toString().trim();
                String new1 = newPasswrodEdit.getText().toString().trim();
                String new2 = newPassword2Edit.getText().toString().trim();
                String cachePassword = SharePreferenceManager.getKeyCachedPassword();
                if (TextUtils.isEmpty(old))
                {
                    NToast.shortToast(context, R.string.original_password);
                    return;
                }
                if (TextUtils.isEmpty(new1))
                {
                    NToast.shortToast(context, R.string.new_password_not_null);
                    return;
                }
                if (new1.length() < 6 || new1.length() > 16)
                {
                    NToast.shortToast(context, R.string.passwords_invalid);
                    return;
                }
                if (TextUtils.isEmpty(new2))
                {
                    NToast.shortToast(context, R.string.confirm_password_not_null);
                    return;
                }
                if (!cachePassword.equals(old))
                {
                    NToast.shortToast(context, R.string.original_password_mistake);
                    return;
                }
                if (!new1.equals(new2))
                {
                    NToast.shortToast(context, R.string.passwords_do_not_match);
                    return;
                }
                callBack.updatePassword(old, new1);
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {

            }

        });
        dialog.show();
    }

}
