package com.petiyaak.box.view;


import com.petiyaak.box.model.respone.BaseRespone;


public interface IBindView extends BaseView{
    void bindSuccess(BaseRespone respone);
    void bindFail(Throwable error, Integer code, String msg);

}
