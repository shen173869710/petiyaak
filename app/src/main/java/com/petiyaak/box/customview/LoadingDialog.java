package com.petiyaak.box.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.petiyaak.box.R;


public class LoadingDialog extends Dialog {

    /**
     * 宽高由布局文件中指定（但是最底层的宽度无效，可以多嵌套一层解决）
     */
    private ImageView iv;
    private AnimationDrawable mAnimationDrawable;
    public LoadingDialog(Context context) {
        super(context);

    }

    public LoadingDialog(Context context, int style) {
        super(context, style);

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
    }

}