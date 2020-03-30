package com.petiyaak.box.view;


import com.petiyaak.box.model.respone.BaseRespone;

/**
 *  用户指纹相关
 */

public interface IFingerView extends BaseView{
    /**
     *        添加指纹相关
     * @param respone
     */
    void addFingerSuccess(BaseRespone respone);
    void addFingerFail(Throwable error, Integer code, String msg);
    /**
     *        获取指纹相关
     * @param respone
     */
    void getFingerSuccess(BaseRespone respone);
    void getFingerFail(Throwable error, Integer code, String msg);

    /**
     *        分享指纹给用户
     * @param respone
     */
    void shareSuccess(BaseRespone respone);
    void shareFail(Throwable error, Integer code, String msg);

}
