package com.petiyaak.box.view;


import com.petiyaak.box.model.respone.BaseRespone;


public interface IPetiyaakInfoView extends BaseView{

    void success(BaseRespone respone);
    void fail(Throwable error, Integer code, String msg);



    void cancleSuccess(BaseRespone respone);
    void cancleFail(Throwable error, Integer code, String msg);
}
