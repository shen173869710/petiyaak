package com.petiyaak.box.presenter;

import com.petiyaak.box.api.HttpManager;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.view.IShareView;
import java.util.TreeMap;


public class SharePresenter extends BasePresenter<IShareView>{

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
     *    分享设备给用户
     */
    public void shareToUser(int userId, int deviceId) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("userId",userId);
        treeMap.put("deviceId",deviceId);
        treeMap.put("leftThumb",1);
        treeMap.put("leftIndex",2);
        treeMap.put("leftMiddle",3);
        treeMap.put("leftRing",4);
        treeMap.put("leftLittle",5);

        treeMap.put("rightThumb",6);
        treeMap.put("rightIndex",7);
        treeMap.put("rightMiddle",8);
        treeMap.put("rightRing",9);
        treeMap.put("rightLittle",10);
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
    public void addFingerprints(int userId, int deviceId, int postion,int fingerId,int isOwner) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("userId",userId);
        treeMap.put("deviceId",deviceId);
        switch (postion) {
            case 1:
                treeMap.put("leftThumb",fingerId);
                break;
            case 2:
                treeMap.put("leftIndex",fingerId);
                break;
            case 3:
                treeMap.put("leftMiddle",fingerId);
                break;
            case 4:
                treeMap.put("leftRing",fingerId);
                break;
            case 5:
                treeMap.put("leftLittle",fingerId);
                break;
            case 6:
                treeMap.put("rightThumb",fingerId);
                break;
            case 7:
                treeMap.put("rightIndex",fingerId);
                break;
            case 8:
                treeMap.put("rightMiddle",fingerId);
                break;
            case 9:
                treeMap.put("rightRing",fingerId);
                break;
            case 10:
                treeMap.put("rightLittle",fingerId);
                break;
        }
        treeMap.put("isOwner",isOwner);
        doHttpTask(getApiService().addFingerprints(treeMap),
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
