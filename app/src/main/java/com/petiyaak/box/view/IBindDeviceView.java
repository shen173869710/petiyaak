package com.petiyaak.box.view;


import com.petiyaak.box.model.respone.BaseRespone;

/**
 * 绑定蓝牙设备
 */

public interface IBindDeviceView extends BaseView{
    void bindSuccess(BaseRespone respone);
    void bindFail(Throwable error, Integer code, String msg);
}
