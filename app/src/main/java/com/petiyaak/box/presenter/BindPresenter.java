package com.petiyaak.box.presenter;



import com.petiyaak.box.api.HttpManager;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.view.IBindView;

import java.util.TreeMap;


public class BindPresenter extends BasePresenter<IBindView>{

    /**
     *    分享设备给用户
     */
    public void bindDeviced(String deviceName, String bluetoothName,String bluetoothMac,int deviceOwnerId) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("deviceName",deviceName);
        treeMap.put("status",1);
        treeMap.put("bluetoothName",bluetoothName);
        treeMap.put("bluetoothMac",bluetoothMac);
        treeMap.put("deviceOwnerId",deviceOwnerId);
        doHttpTask(getApiService().bindDevice(treeMap),
                new HttpManager.OnResultListener() {
                    @Override
                    public void onSuccess(BaseRespone respone) {
                        if (respone != null && respone.isOk() && null !=respone.getData()) {
                            getBaseView().bindSuccess(respone);
                        }else{
                            getBaseView().bindFail(null,-1, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable error, Integer code,String msg) {
                        getBaseView().bindFail(error,code, msg);
                    }
                });
    }


}
