package com.petiyaak.box.view;


import com.petiyaak.box.model.respone.BaseRespone;

/**
 * Created by czl on 2019/7/9.
 * 获取用户列表
 */

public interface IShareUserView extends BaseView{
    void shareSuccess(BaseRespone respone);
    void shareFail(Throwable error, Integer code, String msg);

    void serachSuccess(BaseRespone respone);
    void serachFail(Throwable error, Integer code, String msg);
}
