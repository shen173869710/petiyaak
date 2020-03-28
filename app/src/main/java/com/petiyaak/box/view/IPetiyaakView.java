package com.petiyaak.box.view;


import com.petiyaak.box.model.respone.BaseRespone;


public interface IPetiyaakView extends BaseView{
    void success(BaseRespone respone);
    void fail(Throwable error, Integer code, String msg);

}
