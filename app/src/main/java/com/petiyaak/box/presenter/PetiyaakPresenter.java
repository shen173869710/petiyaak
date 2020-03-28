package com.petiyaak.box.presenter;


import com.petiyaak.box.api.HttpManager;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.view.IPetiyaakView;

import java.util.TreeMap;


public class PetiyaakPresenter extends BasePresenter<IPetiyaakView>{

    /**
     *    获取设备列表
     */
    public void getOwnerFingerprintsList(boolean loading) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("userId", BaseApp.userInfo.getId());
        if (loading) {
            doHttpTask(getApiService().getOwnerFingerprintsList(treeMap),
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
            doHttpTaskWihtDialog(getApiService().getOwnerFingerprintsList(treeMap),
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


}
