package com.petiyaak.box.util;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.petiyaak.box.base.BaseApp;

import me.drakeet.support.toast.ToastCompat;


/**
 * Created by long on 2016/6/6.
 * 避免同样的信息多次触发重复弹出的问题
 */
public class ToastUtils {

    private static String oldMsg;
    protected static ToastCompat toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    private ToastUtils() {
        throw new RuntimeException("ToastUtils cannot be initialized!");
    }

    public static void showToast(String s) {
        try{
            if (toast == null) {
                toast = ToastCompat.makeText(BaseApp.getInstance(), s, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (s.equals(oldMsg)) {
                    if (twoTime - oneTime > Toast.LENGTH_LONG) {
                        toast.show();
                    }
                } else {
                    oldMsg = s;
                    toast.setText(s);
                    toast.show();
                }
                oneTime = twoTime;
            }
        }catch (Exception e){
        }
    }

    public static void showToast(int resId) {
        showToast(BaseApp.getInstance().getString(resId));
    }



}
