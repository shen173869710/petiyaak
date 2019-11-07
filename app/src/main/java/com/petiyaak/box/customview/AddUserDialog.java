package com.petiyaak.box.customview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.petiyaak.box.R;
import com.petiyaak.box.util.ToastUtils;

import butterknife.BindView;


/**
 *
 */
public class AddUserDialog extends Dialog {

    private static AddUserDialog dialog;
    TextView userTitle;
    EditText userName;
    TextView userSave;
    TextView userCancle;
    LinearLayout successLayout;
    private Context mContext;

    public AddUserDialog(Context context, DialogContent content, OnDialogClick onDialogClick) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        init(content, onDialogClick);
    }


    public AddUserDialog(Context context, int theme) {
        super(context, R.style.MyDialog);
    }

    private void init(DialogContent content, OnDialogClick onDialogClick) {
        setContentView(R.layout.dialog_add_user);


        userTitle = findViewById(R.id.user_title);
        if (!TextUtils.isEmpty(content.desc)) {
            userTitle.setText(content.desc);
        }
        userName = findViewById(R.id.user_name);
        userSave = findViewById(R.id.user_save);
        userCancle = findViewById(R.id.user_cancle);
        if (content.hideEidt) {
            userName.setVisibility(View.GONE);
        }else {
            userName.setVisibility(View.VISIBLE);
        }
        userSave.setText(content.ok);
        userSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString().trim();
                if (TextUtils.isEmpty(name) && !content.hideEidt) {
                    ToastUtils.showToast("Username is not empty");
                    return;
                }
                onDialogClick.onDialogOkClick(name);
                dismiss();
            }
        });
        userCancle.setText(content.cancle);
        userCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public static void show(Context context, DialogContent content, OnDialogClick onDialogClick) {
        try {
            if (null == context) {
                return;
            }

            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    return;
                }
            }
            if (onDialogClick == null) {
                return;
            }

            if (dialog != null && dialog.isShowing()) {
                dismiss(context);
                dialog = null;
            }

            dialog = new AddUserDialog(context, content, onDialogClick);
            dialog.show();
        } catch (Exception e) {
        }
    }

    public static void dismiss(Context context) {
        try {
            if (null == context) {
                return;
            }
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    dialog = null;
                    return;
                }
            }
            if (dialog != null && dialog.isShowing()) {
                Context loadContext = dialog.getContext();
                if (loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        dialog = null;
                        return;
                    }
                }
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dialog = null;
        }
    }
}