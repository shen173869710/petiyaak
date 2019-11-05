package com.petiyaak.box.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import com.petiyaak.box.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


public class BaseApp extends Application {
    public static final String TAG = "BaseApp";

    public static List<Activity> activities = new LinkedList<>();
    private static BaseApp mBaseApp;
    private static Context mContext=null;//上下文
    /**
     * 获取BaseApp单例方法
     * @return
     */
    public static BaseApp getInstance() {
        return mBaseApp;
    }



    @Override
    public void onCreate() {
        super.onCreate();
            mBaseApp = this;

    }


    public static void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        BaseApp.mContext = mContext;
    }



}
