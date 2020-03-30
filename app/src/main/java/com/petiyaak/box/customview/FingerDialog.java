package com.petiyaak.box.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.petiyaak.box.R;

public class FingerDialog extends Dialog {

    private OnDialogClick mOnDialogClick;
    public FingerDialog(Context context,OnDialogClick onDialogClick) {
        super(context);
        mOnDialogClick = onDialogClick;
    }


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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_finger, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        onWindowAttributesChanged(params);

        findViewById(R.id.finger_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDialogClick.onDialogOkClick(null);
                dismiss();
            }
        });
    }


}