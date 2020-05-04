package com.petiyaak.box.constant;

import com.petiyaak.box.R;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;

/**
 * Created by chenzhaolin on 2019/11/8.
 */
public class ConstantEntiy {
    public static final String INTENT_BOX = "intent_box";
    public static final String INTENT_USER = "intent_user";
    public static final String INTENT_BIND = "intent_bind";
    /**
     *  开始注册指纹
     */
    public static final String ATFAE = "ATFAE";
    public static final String ATFAE_START = "START";
    public static final String ATFAE_OK = "ATFAE";
    public static final String ATFAE_FAIL1 = "FAIL1";
    public static final String ATFAE_FAIL2 = "FAIL2";
    public static final String ATFAE_FAIL3 = "FAIL3";
    public static final String ATFAE_FAIL4 = "FAIL4";
    /**
     *  设置用户名密码
     *  ATSTP=li&1234
     *  ATSTP=OK
     */

    /**
     *  设置黄灯闪烁次数(默认次数3次)ATLYO不带参数就是3
     *  ATLYO=2
     *  ATLYO=OK
     *  设置橙灯闪烁次数
     *  ATLOO
     *  设置兰灯闪烁次数
     *  ATLBO
     *  ATLBO=OK
     *  设置绿灯闪烁次数
     *  ATLGO
     *  设置红灯闪烁次数
     *  ATLRO
     *  设置白灯闪烁次数
     *  ATLWO
     */

    /**
     *  开锁
     *  ATLKO
     *  ATLKO=OK
     */
    public static final String ATLKO = "ATLKO";
    public static final String ATLKO_OK = "ATLKO=OK";
    /**
     *   删除指纹
     *   ATFDE=2
     *   指纹删除2ATFDE=2 ，ATFDE=999删除所有
     *   ATFDE=OK:2       OK后面对应ID
     */


    public static final String ATFDE = "ATFDE";
    public static final String ATFDE_OK = "ATFDE=OK";
    /**
     *        根据删除的id 获取命令
     * @param id
     * @return
     */
    public static String getATFDEstirng(int id) {
        return  ATFDE + "=" + id;
    }
    /**
     * 指纹ID获取
     *  ATFGD
     *
     *  ATFGD=OK:
     *  ATFGD=NULL
     *  ATFGD=FAIL
     */

    /**
     *  用户登陆
     *  ATLOG=li&1234
     *  ATLOG=OK
     *  ATLOG=FAIL
     */

    /**
     *       根据位置获取指纹
     * @param info
     * @param postion
     * @return
     */
    public static int getFingerId(PetiyaakBoxInfo info, int postion) {
        if (info == null) {
            return -1;
        }
        switch (postion) {
            case 1:
                return info.getLeftThumb();
            case 2:
                return info.getLeftIndex();
            case 3:
                return info.getLeftMiddle();
            case 4:
                return info.getLeftRing();
            case 5:
                return info.getLeftLittle();
            case 6:
                return info.getRightThumb();
            case 7:
                return info.getRightIndex();
            case 8:
                return info.getLeftMiddle();
            case 9:
                return info.getLeftRing();
            case 10:
                return info.getLeftLittle();
            default:
                return -1;
        }
    }

    public static int [] USER_HEAD_ID = {
            R.mipmap.head_1,
            R.mipmap.head_2,
            R.mipmap.head_3,
            R.mipmap.head_4,
            R.mipmap.head_5,
            R.mipmap.head_6,
            R.mipmap.head_7,
            R.mipmap.head_8,
            R.mipmap.head_9,
            R.mipmap.head_10,
            R.mipmap.head_1,
    };

    public static final String ATVOL = "ATVOL";
}
