package com.petiyaak.box.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.petiyaak.box.model.bean.UserInfo;
import com.tencent.bugly.crashreport.CrashReport;

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

    public static UserInfo userInfo;


    @Override
    public void onCreate() {
        super.onCreate();
            mBaseApp = this;

        CrashReport.initCrashReport(getApplicationContext(), "a28fd1f967", false);



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
