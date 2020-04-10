package com.petiyaak.box.presenter;

import com.petiyaak.box.api.HttpManager;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.view.IFingerDelView;

import java.util.TreeMap;


public class FingerDelPresenter extends BasePresenter<IFingerDelView>{
    /**
     *    删除一个指纹
     */
    public void delFingerprints(int userId, int deviceId, int postion,int fingerId,boolean bind) {
        int isOwner = 0;
        if (bind) {
            isOwner = 1;
        }else {
            isOwner = 2;
        }
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
                            getBaseView().delFingerSuccess(respone);
                        }else{
                            getBaseView().delFingerFail(null,-1, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable error, Integer code,String msg) {
                        getBaseView().delFingerFail(error,code, msg);
                    }
                });
    }


    /**
     *    取消分享给用户
     */
    public void cancelShareDevice(int userId, int deviceId) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("userId",userId);
        treeMap.put("deviceId",deviceId);
        doHttpTask(getApiService().cancelShareDevice(treeMap),
                new HttpManager.OnResultListener() {
                    @Override
                    public void onSuccess(BaseRespone respone) {
                        if (respone != null && respone.isOk()) {
                            getBaseView().cancleSuccess(respone);
                        }else{
                            getBaseView().cancleFail(null,-1, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable error, Integer code,String msg) {
                        getBaseView().cancleFail(error,code, msg);
                    }
                });
    }

}
