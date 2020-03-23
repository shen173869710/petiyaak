package com.petiyaak.box.presenter;



import com.petiyaak.box.api.HttpManager;
import com.petiyaak.box.model.requset.BaseRequest;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.view.ILoginView;

import java.util.TreeMap;

/**
 * Created by czl on 2019/7/9.
 * 用户登录相关逻辑业务
 */

public class LoginPresenter extends BasePresenter<ILoginView>{

    /**
     *  设备激
     * **/
    public void doLogin(String loginName,String pwd) {
       TreeMap<String, Object> treeMap = new TreeMap<>();
       treeMap.put("username",loginName);
       treeMap.put("password", pwd);
       doHttpTask(getApiService().login(treeMap),
               new HttpManager.OnResultListener() {
            @Override
            public void onSuccess(BaseRespone respone) {
                if (respone != null && respone.isOk() && null !=respone.getData()) {
                    getBaseView().loginSuccess(respone);
                }else{
                    getBaseView().loginFail(null,-1, respone.getMessage());
                }
            }

            @Override
            public void onError(Throwable error, Integer code,String msg) {
                getBaseView().loginFail(error,code, msg);
            }
       });
    }

    /**
     *
     * 登录请求
     * **/
    public void doRegister(String userName, final String pwd) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("username",userName);
        treeMap.put("password",pwd);
        doHttpTask(getApiService().register(treeMap), new HttpManager.OnResultListener() {
            @Override
            public void onSuccess(BaseRespone respone) {
                getBaseView().registerSuccess(respone);
            }
            @Override
            public void onError(Throwable error, Integer code,String msg) {
                getBaseView().registerFail(error,code,msg);
            }
        });
    }

}
