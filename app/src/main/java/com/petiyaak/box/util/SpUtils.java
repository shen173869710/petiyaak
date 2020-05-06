package com.petiyaak.box.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.petiyaak.box.base.BaseApp;

import java.util.Map;


public class SpUtils {
    private SharedPreferences share;
    private SharedPreferences.Editor editor;

    //登录的账号密码
    public static final String SAVE_PWD = "save_pwd";
    public static final String SAVE_ACCOUNT = "save_account";



    /**
     * 单例模式
     */
    private static SpUtils instance;//单例模式 双重检查锁定
    public static SpUtils getInstance() {
        if (instance == null) {
            synchronized (SpUtils.class) {
                if (instance == null) {
                    instance = new SpUtils();
                }
            }
        }
        return instance;
    }

    private SpUtils() {
        share = PreferenceManager.getDefaultSharedPreferences(BaseApp.getInstance());
        editor = share.edit();
    }

    /**
     * ------- Int ---------
     */
    public void putInt(String spName, int value) {
        editor.putInt(spName, value);
        editor.commit();
    }

    public int getInt(String spName, int defaultvalue) {
        return share.getInt(spName, defaultvalue);
    }

    /**
     * ------- String ---------
     */
    public boolean putString(String spName, String value) {
        editor.putString(spName, value);
        return editor.commit();
    }

    public String getString(String spName, String defaultvalue) {
        return share.getString(spName, defaultvalue);
    }

    public String getString(String spName) {
        return share.getString(spName, "");
    }


    /**
     * ------- boolean ---------
     */
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return share.getBoolean(key, defValue);
    }

    /**
     * ------- float ---------
     */
    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getFloat(String key, float defValue) {
        return share.getFloat(key, defValue);
    }


    /**
     * ------- long ---------
     */
    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key, long defValue) {
        return share.getLong(key, defValue);
    }

    /**
     * 清空SP里所有数据 谨慎调用
     */
    public void clear() {
        editor.clear();//清空
        editor.commit();//提交
    }

    /**
     * 删除SP里指定key对应的数据项
     *
     * @param key
     */
    public boolean remove(String key) {
        editor.remove(key);//删除掉指定的值
        return editor.commit();//提交
    }

    /**
     * 查看sp文件里面是否存在此 key
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return share.contains(key);
    }


    public void deleteByPrefixKey(String prefixKey){
        try{
            Map<String,?> map = share.getAll();
            if(null == map || map.isEmpty()){
                return;
            }

            for (String key : map.keySet()) {
                if(key.contains(prefixKey)){
                    editor.remove(key);
                    editor.apply();
                }
            }
        }catch (Exception e){
            LogUtils.e("=========",e.getMessage());
        }
    }
}
