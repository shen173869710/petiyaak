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


public class FingerDialog extends Dialog {
    private ImageView iv;
    public FingerDialog(Context context) {
        super(context);

    }

    public FingerDialog(Context context, int style) {
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
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }


}