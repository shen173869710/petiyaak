package com.petiyaak.box.presenter;

import com.petiyaak.box.api.HttpManager;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.view.IShareUserView;
import java.util.TreeMap;


public class ShareUserPresenter extends BasePresenter<IShareUserView>{

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
                            getBaseView().serachSuccess(respone);
                        }else{
                            getBaseView().serachFail(null,-1, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable error, Integer code,String msg) {
                        getBaseView().serachFail(error,code, msg);
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
                            getBaseView().shareSuccess(respone);
                        }else{
                            getBaseView().shareFail(null,-1, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable error, Integer code,String msg) {
                        getBaseView().shareFail(error,code, msg);
                    }
                });
    }

}
