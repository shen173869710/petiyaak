package com.petiyaak.box.constant;

/**
 * Created by chenzhaolin on 2019/11/8.
 */
public class CmdEntiy {
    /**
     * 设置用户名和新密码 默认密码：1234
     * ATSTP=li&1234
     * ATSTP=OK
     */

    public static byte[] setPwd(String name, String pwd) {
        String cmd = "ATSTP="+name+"&"+pwd;
        return cmd.getBytes();
    }

    /**
     * 设置黄灯闪烁次数(默认次数3次)ATLYO不带参数就是3
     * ATLYO=2
     * ATLYO=OK
     */


    /**
     * 设置橙灯闪烁次数
     * ATLOO
     * ATLOO=OK
     */

    /**
     * 设置兰灯闪烁次数
     * ATLBO
     * ATLBO=OK
     */

    /**
     * 设置绿灯闪烁次数
     * ATLGO
     * ATLGO=OK
     */

    /**
     * 设置红灯闪烁次数
     * ATLRO
     * ATLRO=OK
     */

    /**
     * 设置白灯闪烁次数
     * ATLWO
     * ATLWO=OK
     */

    /**
     * 开锁
     * ATLKO
     * ATLKO=OK
     */

    /**
     * 指纹删除2ATFDE=2 ，ATFDE=999删除所有
     * ATFDE=2
     * ATFDE=OK:2
     */



    /**
     * 指纹心跳测试，检测指纹模组工作是否正常
     * ATFHT
     * ATFHT=OK或者ATFHT=FAIL
     */


    /**
     * 指纹ID获取
     * ATFGD
     * ATFGD=OK:  后为100 short类型ID列表
     * ATFGD=NULL
     * ATFGD=FAIL
     */


    /**
     * 指纹自动注册
     * ATFAE
     * ATFAE=OK-30/100   进度30%
     * ATFAE=OK-ID=2     注册成功,返回注册ID为2
     * ATFAE=FAIL1       存储出问题
     * ATFAE=FAIL2       重复指纹，可提示用户换手指按压
     * ATFAE=FAIL3       采图质量不好，继续按压直到成功
     * ATFAE=FAIL4       8S超时还没按压手指，继续按压直到注册成功
     */


    /**
     * 指纹匹配
     * ATFMT
     * ATFMT=OK-TOUCH  指纹按上了
     * ATFMT=OK_YES    检测到指纹
     * ATFMT=OK_NO     没检测到指纹

     */


    /**
     * 设置系统策略SetSystemStrategy 20(0x16)
     * ATSSS=22
     * ATSSS=OK
     * ATSSS=FAIL
     */

    /**
     * 同步时间2019年9月26日17点12分06秒
     * ATSTM=20190926171206
     * ATSTM=OK
     */

    /**
     * 用户登录
     * ATLOG=OK
     * ATLOG=FAIL
     */
}
