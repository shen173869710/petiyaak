package com.petiyaak.box.customview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.petiyaak.box.R;


/**
 *
 */
public class OptionDialog extends Dialog {

    private static OptionDialog optionDialog;
    private Context context;
    TextView cancle;
    TextView desc;
    TextView ok;
    TextView icon;

    public OptionDialog(Context context, DialogContent content, OnDialogClick onDialogClick) {
        super(context, R.style.MyDialog);
        this.context = context;
        init(content,onDialogClick);
    }

    public OptionDialog(Context context, int theme) {
        super(context, R.style.MyDialog);
    }

    private void init(DialogContent content, OnDialogClick onDialogClick) {
        setContentView(R.layout.dialog_option);
        setCanceledOnTouchOutside(content.touchOutside);
        cancle = findViewById(R.id.dialog_cancle);
        desc = findViewById(R.id.dialog_desc);
        ok = findViewById(R.id.dialog_ok);
        icon = findViewById(R.id.dialog_icon);
        if (content.cancelable) {
            setCancelable(false);
        }else {
            setCancelable(true);
        }
        if (content.resId > 0) {
            icon.setBackgroundResource(content.resId);
        }

        if(null != content.spanned){
            desc.setText(content.spanned);
        }else{
            if (!TextUtils.isEmpty(content.desc)) {
                if(content.desc.contains("font")) {
                    desc.setText(Html.fromHtml(content.desc));
                }else {
                    desc.setText(content.desc);
                }
            }
        }

        if (!TextUtils.isEmpty(content.cancle)) {
            cancle.setText(content.cancle);
        }


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onDialogClick) {
                    onDialogClick.onDialogCloseClick(null);
                    OptionDialog.dismiss(context);
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onDialogClick) {
                    onDialogClick.onDialogOkClick(null);
                    OptionDialog.dismiss(context);
                }
            }
        });

        if (!TextUtils.isEmpty(content.ok)) {
            ok.setText(content.ok);
        }




        if (content.hideOk) {
            ok.setVisibility(View.GONE);
        }else {
            ok.setVisibility(View.VISIBLE);
        }

        if (content.hideCancle) {
            cancle.setVisibility(View.GONE);
        }else {
            cancle.setVisibility(View.VISIBLE);
        }

    }


    public static void show(Context context, DialogContent content, OnDialogClick onDialogClick) {
        try{
            if(null ==context){
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

            if (optionDialog != null && optionDialog.isShowing()) {
                optionDialog.dismiss();
                optionDialog = null;
            }

            optionDialog = new OptionDialog(context, content,onDialogClick);
            optionDialog.show();
        }catch (Exception e){

        }
    }


    public static void dismiss(Context context) {
        try {
            if(null ==context){
                return;
            }

            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    optionDialog = null;
                    return;
                }
            }
            if (optionDialog != null && optionDialog.isShowing()) {
                Context loadContext = optionDialog.getContext();
                if (loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        optionDialog = null;
                        return;
                    }
                }

                optionDialog.dismiss();
                optionDialog = null;
            }
        } catch (Exception e) {
        } finally {
            optionDialog = null;
        }
    }
}