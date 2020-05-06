package com.petiyaak.box.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.petiyaak.box.R;
import com.petiyaak.box.event.DialogDIsmissEvent;
import com.petiyaak.box.util.LogUtils;

import org.greenrobot.eventbus.EventBus;


public class LoadingDialog extends Dialog {

    /**
     * 宽高由布局文件中指定（但是最底层的宽度无效，可以多嵌套一层解决）
     */
    private ImageView iv;
    private AnimationDrawable mAnimationDrawable;
    private TextView tv;
    private String mTitle;

    public LoadingDialog(Context context) {
        super(context);

    }

    public LoadingDialog(Context context, int style) {
        super(context, style);
    }

    public LoadingDialog(Context context, int style, String title) {
        super(context, style);
        this.mTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null);
        setContentView(view);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        iv = (ImageView) view.findViewById(R.id.loading_image);
        iv.setImageResource(R.drawable.main_loading);

        tv = findViewById(R.id.loading_text);

        if (TextUtils.isEmpty(mTitle)) {
            tv.setVisibility(View.GONE);
        }else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(mTitle);
        }
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    @Override
    public void show() {
        // TODO Auto-generated method stub
        super.show();
        if (null != iv && null !=iv.getDrawable()) {
            mAnimationDrawable = (AnimationDrawable) iv.getDrawable();
            if (null != mAnimationDrawable){
                mAnimationDrawable.start();
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (null != iv && null !=iv.getDrawable()) {
            mAnimationDrawable = (AnimationDrawable) iv.getDrawable();
            if (null != mAnimationDrawable){
                mAnimationDrawable.stop();
            }
        }
        EventBus.getDefault().post(new DialogDIsmissEvent());
    }

}