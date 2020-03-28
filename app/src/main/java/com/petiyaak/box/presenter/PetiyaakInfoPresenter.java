package com.petiyaak.box.presenter;

import com.petiyaak.box.api.HttpManager;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.view.IPetiyaakInfoView;
import com.petiyaak.box.view.IShareView;

import java.util.TreeMap;


public class PetiyaakInfoPresenter extends BasePresenter<IPetiyaakInfoView>{
    /**
     *    根据设备ID 查询分享的用户
     */
    public void getUserListByDeviceId(int deviceId, boolean isLoading) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("deviceId",deviceId);

        if (isLoading) {
            doHttpTask(getApiService().getUserListByDeviceId(treeMap),
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
        }else {
            doHttpTaskWihtDialog(getApiService().getUserListByDeviceId(treeMap),
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
                        if (respone != null && respone.isOk() && null !=respone.getData()) {
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
