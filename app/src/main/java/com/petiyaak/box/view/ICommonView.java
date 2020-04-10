package com.petiyaak.box.view;
import com.petiyaak.box.model.respone.BaseRespone;

/**
 * Created by czl on 2019/7/9.
 * 获取用户列表
 */

public interface ICommonView extends BaseView{
    void success(BaseRespone respone);
    void fail(Throwable error, Integer code, String msg);

}
