package com.petiyaak.box.view;


import com.petiyaak.box.model.respone.BaseRespone;

/**
 * Created by czl on 2019/7/9.
 * 登录页面抽象的接口
 */

public interface ILoginView extends BaseView{

    /***登录成功**/
    void loginSuccess(BaseRespone respone);
    /***登录失败**/
    void loginFail(Throwable error, Integer code, String msg);


    /***激活成功**/
    void registerSuccess(BaseRespone respone);
    /***激活失败**/
    void registerFail(Throwable error, Integer code, String msg);
}
