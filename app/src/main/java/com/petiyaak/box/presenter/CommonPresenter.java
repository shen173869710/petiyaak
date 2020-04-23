package com.petiyaak.box.presenter;

import com.petiyaak.box.api.HttpManager;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.view.ICommonView;

import java.util.TreeMap;


public class CommonPresenter extends BasePresenter<ICommonView>{

    /**
     *        查询用户列表
     */
    public void getUserInfoList(String username) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("username",username);
        doHttpTask(getApiService().getuserList(treeMap),
                new HttpManager.OnResultListener() {
                    @Override
                    public void onSuccess(BaseRespone respone) {
                        if (respone != null && respone.isOk() && null !=respone.getData()) {
                            getBaseView().success(respone);
                        }else{
                            getBaseView().fail(null,-1, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable error, Integer code,String msg) {
                        getBaseView().fail(error,code, msg);
                    }
                });
    }



    /**
     *    根据设备ID 查询分享的用户
     */
    public void checkVersion(int versionCode) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("channelId","petiyaak");
        treeMap.put("versionCode",versionCode);
        doHttpTaskWihtDialog(getApiService().checkVersion(treeMap),
                new HttpManager.OnResultListener() {
                    @Override
                    public void onSuccess(BaseRespone respone) {
                        if (respone != null && respone.isOk() && null !=respone.getData()) {
                            getBaseView().success(respone);
                        }else{
                            getBaseView().fail(null,-1, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable error, Integer code,String msg) {
                        getBaseView().fail(error,code, msg);
                    }
                });
    }


    /**
     *    用户ID, 设备ID 获取用户指纹信息
     */
    public void getFingerprints(int userId, int deviceId) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("userId",userId);
        treeMap.put("deviceId",deviceId);
        doHttpTaskWihtDialog(getApiService().getFingerprints(treeMap),
                new HttpManager.OnResultListener() {
                    @Override
                    public void onSuccess(BaseRespone respone) {
                        if (respone != null && respone.isOk() && null !=respone.getData()) {
                            getBaseView().success(respone);
                        }else{
                            getBaseView().fail(null,-1, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable error, Integer code,String msg) {
                        getBaseView().fail(error,code, msg);
                    }
                });
    }


    /**
     *    分享设备给用户
     */
    public void shareToUser(int userId, PetiyaakBoxInfo info) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("userId",userId);
        treeMap.put("deviceId",info.getDeviceId());
        treeMap.put("isOwner",2);
        doHttpTask(getApiService().shareDevice(treeMap),
                new HttpManager.OnResultListener() {
                    @Override
                    public void onSuccess(BaseRespone respone) {
                        if (respone != null && respone.isOk() && null !=respone.getData()) {
                            getBaseView().success(respone);
                        }else{
                            getBaseView().fail(null,-1, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable error, Integer code,String msg) {
                        getBaseView().fail(error,code, msg);
                    }
                });
    }


    /**
     *    用户删除一个设备
     */
    public void delBox(int userId, PetiyaakBoxInfo info) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("userId",userId);
        treeMap.put("deviceId",info.getDeviceId());
        treeMap.put("isOwner",2);
        doHttpTask(getApiService().shareDevice(treeMap),
                new HttpManager.OnResultListener() {
                    @Override
                    public void onSuccess(BaseRespone respone) {
                        if (respone != null && respone.isOk() && null !=respone.getData()) {
                            getBaseView().success(respone);
                        }else{
                            getBaseView().fail(null,-1, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable error, Integer code,String msg) {
                        getBaseView().fail(error,code, msg);
                    }
                });
    }

}
