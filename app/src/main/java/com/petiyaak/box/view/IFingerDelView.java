package com.petiyaak.box.view;


import com.petiyaak.box.model.respone.BaseRespone;

/**
 *  用户指纹相关
 */

public interface IFingerDelView extends BaseView{
    /**
     *        删除一个指纹
     * @param respone
     */
    void delFingerSuccess(BaseRespone respone);
    void delFingerFail(Throwable error, Integer code, String msg);
    /**
     *        获取指纹相关
     * @param respone
     */
    void cancleSuccess(BaseRespone respone);
    void cancleFail(Throwable error, Integer code, String msg);

}
