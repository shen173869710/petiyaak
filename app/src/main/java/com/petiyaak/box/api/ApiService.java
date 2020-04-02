package com.petiyaak.box.api;

import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.model.respone.BindDeviceRespone;
import com.petiyaak.box.model.respone.LoginRespone;
import com.petiyaak.box.model.respone.UserListRespone;
import com.petiyaak.box.model.respone.VersionRespone;

import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
/**
 * 请求的相关接口
 */
public interface ApiService {

    /**
     *  登陆的接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/api/login")
    Observable<BaseRespone<UserInfo>> login(@FieldMap Map<String, Object> map);

    /**
     *  用户登录接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/api/register")
    Observable<BaseRespone<LoginRespone>> register(@FieldMap Map<String, Object> map);

    /**
     *    3、修改用户接口
     *     地址：http://52.177.190.126:8081/api/updateById
     *     POST
     *     Content-Type:application/x-www-form-urlencoded
     *     请求参数	参数类型	是否为空	备注
     *     userId	Integer	N	用户名
     *     nickname	String	Y	昵称
     *     email	String	Y	邮件
     *     phonenumber	String	Y	手机号码
     *     sex	Int	Y	性别:1=男,2=女
     *     响应结果：
     * {
     *   "code": 200,
     *   "data": {
     *     "id": 6,
     *     "inTime": "2020-03-23 17:53:01",
     *     "roleId": 2,
     * "username": "lyp2",
     * “nickname”:”rayping”,
     * “email”:”379469161@qq.com”,
     * “phonenumber”:”13265091020”,
     *  “sex”:1,
     * “avatarId”:1   //用户头像id
     *
     *   },
     *   "message": "成功"
     * }
     */
    @FormUrlEncoded
    @POST("/api/updateById")
    Observable<BaseRespone<LoginRespone>> changeUserInfo(@FieldMap Map<String, Object> map);
    /**
     *  4、根据条件查询用户信息
     *     地址：http://52.177.190.126:8081/api/queryBy
     *     POST
     *     Content-Type:application/x-www-form-urlencoded
     *     请求参数	参数类型	是否为空	备注
     *     userId	Integer	Y	用户名	 4选一
     *             如果4个参数均为空,则返回无结果
     *     nickname	String	Y	昵称
     *     email	String	Y	邮件
     *     phonenumber	String	Y	手机号码
     *     响应结果：
     *     {
     *         "code": 200,
     *             "data": [{
     *         "id": 6,
     *                 "inTime": "2020-03-23 17:53:01",
     *                 "roleId": 2,
     *                 "username": "lyp2",
     * “nickname”:”rayping”,
     * “email”:”379469161@qq.com”,
     * “phonenumber”:”13265091020”,
     *  “sex”:1,
     * “avatarId”:1
     *     }],
     *         "message": "成功"
     *     }
     */
    @FormUrlEncoded
    @POST("/api/queryBy")
    Observable<BaseRespone<List<UserInfo>>> getuserList(@FieldMap Map<String, Object> map);
/**
 * 5、apk升级接口
 *     地址：http://52.177.190.126:8081/api/checkVersion
 *     POST
 *     Content-Type:application/x-www-form-urlencoded
 *     请求参数	参数类型	是否为空	备注
 *     channelId	String	N	渠道id,写死值petiyaak
 *     versionCode	Integer	N	版本号,例如值100，需要服务器配置大于versioinCode>100才有版本升级
 *     响应结果：
 *     {
 *         "code": 200,
 *             "data": {
 *         "fileUrl": "http://192.168.0.105:8081/apk/petiyaak_box_v1.0.0_release.apk",
 *                 "isForce": true,
 *                 "versionName": "android版本",
 *                 "content": "android版本",
 *                 "versionCode": 101,
 *                 "isUpdate": true
 *     },
 *         "message": "SUCCESS"
 *     }
 *
 *     响应参数	参数类型	是否为空	备注
 *     fileUrl	String	N	文件下载地址
 *     isForce	Boolean	N	是否强制升级
 *     versionName	String	Y	版本名称
 *     content	String	N	更新内容，支持html内容
 *     versionCode	Integer	N	升级后的版本code
 *     isUpdate	Boolean	N	是否需要升级
 */
@FormUrlEncoded
@POST("/api/checkVersion")
Observable<BaseRespone<VersionRespone>> checkVersion(@FieldMap Map<String, Object> map);
/**
 * 6、设备绑定接口
 *     地址：http://52.177.190.126:8081/api/device/bind
 *     POST
 *     Content-Type:application/x-www-form-urlencoded
 *     请求参数	参数类型	是否为空	备注
 *     deviceName	String	N	设备名称
 *     status	Integer	N	设备状态0=未绑定,1=已绑定
 *     bluetoothName	String	N	蓝牙名称
 *     bluetoothMac	String	N	蓝牙mac地址
 *     deviceOwnerId	Int	N	设备拥有者用户id
 *     响应结果：
 *     {
 *         "code": 200,
 *             "data": {
 *         "bluetoothMac": "289w112-4454545~4545",
 *                 "bluetoothName": "wiki",
 *                 "deviceBindTime": 1585379096791,
 *                 "deviceId": 1,
 *                 "deviceName": "petiyaak",
 *                 "deviceOwnerId": 1,
 *                 "imageUrl": "",
 *                 "imageUrlId": 3,
 *                 "status": 1
 *     },
 *         "message": "成功"
 *     }
 *     响应参数	参数类型	备注
 *     bluetoothMac	String	蓝牙mac地址
 *     bluetoothName	Boolean	蓝牙名称
 *     deviceBindTime	Long	设备绑定时间,时间戳格式
 *     deviceId	Int	设备id
 *     deviceName	String	设备名称
 *     deviceOwnerId	Int	设备拥有者用户id
 *     imageUrlId	int	设备生产图片id,值访问1~10,生成以后为固定值
 *     status	Int	设备状态0=未绑定,1=已绑定
 */
@FormUrlEncoded
@POST("/api/device/bind")
Observable<BaseRespone<BindDeviceRespone>> bindDevice(@FieldMap Map<String, Object> map);
/**
 * 7、添加设备指纹记录
 *     地址：http://52.177.190.126:8081/api/device/addFingerprints
 *     POST
 *     Content-Type:application/x-www-form-urlencoded
 *     请求参数	参数类型	是否为空	备注
 *     userId	Integer	N	登录用户id
 *     deviceId	Integer	N	设备id
 *     leftThumb	Integer	N	指纹
 *     leftIndex	String	N
 *     leftMiddle	Int	N
 *     leftRing	Int	N
 *     leftLittle	Int	N
 *     rightThumb	Int	N
 *     rightIndex	Int	N
 *     rightMiddle	Int	N
 *     rightRing	Int	N
 *     rightLittle	Int	N
 *     isOwner	Int	N	是否拥有者,1=是,2=否
 *     响应数据：
 *     {
 *         "code": 200,
 *             "data": {
 *         "bluetoothMac": "289w112-4454545~4545",   //蓝牙mac
 *                 "bluetoothName": "wiki",   //蓝牙名称
 *                 "ctime": 1585389139569,
 *                 "deviceId": 1,   //设备主键id
 *                 "deviceName": "petiyaak",   //设备名称
 *                 "id": 2,
 *                 "isOwner": 1,
 *                 "leftIndex": 13,
 *                 "leftLittle": 16,
 *                 "leftMiddle": 14,
 *                 "leftRing": 15,
 *                 "leftThumb": 12,
 *                 "nickName": "",
 *                 "rightIndex": 23,
 *                 "rightLittle": 26,
 *                 "rightMiddle": 24,
 *                 "rightRing": 25,
 *                 "rightThumb": 22,
 *                 "userId": 1,
 *                 "utime": 1585389139811   //更新时间,时间戳格式
 *     },
 *         "message": "成功"
 *     }
 */
@FormUrlEncoded
@POST("/api/device/addFingerprints")
Observable<BaseRespone<PetiyaakBoxInfo>> addFingerprints(@FieldMap Map<String, Object> map);
/**
 * 8、分享设备给用户接口
 *     地址：http://52.177.190.126:8081/api/device/shareDevice
 *     POST
 *     Content-Type:application/x-www-form-urlencoded
 *     请求参数	参数类型	是否为空	备注
 *     userId	Integer	N	分享的用户id
 *     deviceId	Integer	N	设备id
 *     leftThumb	Integer	N	指纹
 *     leftIndex	String	N
 *     leftMiddle	Int	N
 *     leftRing	Int	N
 *     leftLittle	Int	N
 *     rightThumb	Int	N
 *     rightIndex	Int	N
 *     rightMiddle	Int	N
 *     rightRing	Int	N
 *     rightLittle	Int	N
 *     isOwner	Int	N	是否拥有者,1=是,2=否
 *     响应数据：
 *     {
 *         "code": 200,
 *             "data": {
 *         "bluetoothMac": "289w112-4454545~4545",   //蓝牙mac
 *                 "bluetoothName": "wiki",   //蓝牙名称
 *                 "ctime": 1585389139569,
 *                 "deviceId": 1,   //设备主键id
 *                 "deviceName": "petiyaak",   //设备名称
 *                 "id": 2,
 *                 "isOwner": 1,
 *                 "leftIndex": 13,
 *                 "leftLittle": 16,
 *                 "leftMiddle": 14,
 *                 "leftRing": 15,
 *                 "leftThumb": 12,
 *                 "nickName": "",
 *                 "rightIndex": 23,
 *                 "rightLittle": 26,
 *                 "rightMiddle": 24,
 *                 "rightRing": 25,
 *                 "rightThumb": 22,
 *                 "userId": 1,
 *                 "utime": 1585389139811   //更新时间,时间戳格式
 *     },
 *         "message": "成功"
 *     }
 */
@FormUrlEncoded
@POST("/api/device/shareDevice")
Observable<BaseRespone<PetiyaakBoxInfo>> shareDevice(@FieldMap Map<String, Object> map);
/**
 * 9、用户ID, 设备ID 获取用户指纹信息
 *     地址：http://52.177.190.126:8081/api/device/getFingerprints
 *     POST
 *     Content-Type:application/x-www-form-urlencoded
 *     请求参数	参数类型	是否为空	备注
 *     userId	Integer	N	用户id
 *     deviceId	Integer	N	设备id
 *     响应数据：
 *     {
 *         "code": 200,
 *             "data": {
 *         "bluetoothMac": "289w112-4454545~4545",   //蓝牙mac
 *                 "bluetoothName": "wiki",   //蓝牙名称
 *                 "ctime": 1585389139569,
 *                 "deviceId": 1,   //设备主键id
 *                 "deviceName": "petiyaak",   //设备名称
 *                 "id": 2,
 *                 "isOwner": 1,
 *                 "leftIndex": 13,
 *                 "leftLittle": 16,
 *                 "leftMiddle": 14,
 *                 "leftRing": 15,
 *                 "leftThumb": 12,
 *                 "nickName": "",
 *                 "rightIndex": 23,
 *                 "rightLittle": 26,
 *                 "rightMiddle": 24,
 *                 "rightRing": 25,
 *                 "rightThumb": 22,
 *                 "userId": 1,
 *                 "utime": 1585389139811   //更新时间,时间戳格式
 *     },
 *         "message": "成功"
 *     }
 */
@FormUrlEncoded
@POST("/api/device/getFingerprints")
Observable<BaseRespone<PetiyaakBoxInfo>> getFingerprints(@FieldMap Map<String, Object> map);
/**
 * 10、根据用户id,获取我拥有的设备指纹列表
 *     地址：http://52.177.190.126:8081/api/device/getOwnerFingerprintsList
 *     POST
 *     Content-Type:application/x-www-form-urlencoded
 *     请求参数	参数类型	是否为空	备注
 *     userId	Integer	N	用户id
 *     响应数据：
 *     {
 *         "code": 200,
 *             "data": {
 *         "bluetoothMac": "289w112-4454545~4545",   //蓝牙mac
 *                 "bluetoothName": "wiki",   //蓝牙名称
 *                 "ctime": 1585389139569,
 *                 "deviceId": 1,   //设备主键id
 *                 "deviceName": "petiyaak",   //设备名称
 *                 "id": 2,
 *                 "isOwner": 1,
 *                 "leftIndex": 13,
 *                 "leftLittle": 16,
 *                 "leftMiddle": 14,
 *                 "leftRing": 15,
 *                 "leftThumb": 12,
 *                 "nickName": "",
 *                 "rightIndex": 23,
 *                 "rightLittle": 26,
 *                 "rightMiddle": 24,
 *                 "rightRing": 25,
 *                 "rightThumb": 22,
 *                 "userId": 1,
 *                 "utime": 1585389139811   //更新时间,时间戳格式
 *     },
 *         "message": "成功"
 *     }
 */
@FormUrlEncoded
@POST("/api/device/getOwnerFingerprintsList")
Observable<BaseRespone<List<PetiyaakBoxInfo>>> getOwnerFingerprintsList(@FieldMap Map<String, Object> map);
/**
 * 10、根据用户id,获取我拥有的设备指纹列表
 *     地址：http://52.177.190.126:8081/api/device/getCanUseredFingerprintsList
 *     POST
 *     Content-Type:application/x-www-form-urlencoded
 *     请求参数	参数类型	是否为空	备注
 *     userId	Integer	N	用户id
 *     响应数据：
 *     {
 *         "code": 200,
 *             "data": {
 *         "bluetoothMac": "289w112-4454545~4545",   //蓝牙mac
 *                 "bluetoothName": "wiki",   //蓝牙名称
 *                 "ctime": 1585389139569,
 *                 "deviceId": 1,   //设备主键id
 *                 "deviceName": "petiyaak",   //设备名称
 *                 "id": 2,
 *                 "isOwner": 1,
 *                 "leftIndex": 13,
 *                 "leftLittle": 16,
 *                 "leftMiddle": 14,
 *                 "leftRing": 15,
 *                 "leftThumb": 12,
 *                 "nickName": "",
 *                 "rightIndex": 23,
 *                 "rightLittle": 26,
 *                 "rightMiddle": 24,
 *                 "rightRing": 25,
 *                 "rightThumb": 22,
 *                 "userId": 1,
 *                 "utime": 1585389139811   //更新时间,时间戳格式
 *     },
 *         "message": "成功"
 *     }
 */
@FormUrlEncoded
@POST("/api/device/getCanUseredFingerprintsList")
Observable<BaseRespone<List<PetiyaakBoxInfo>>> getCanUseredFingerprintsList(@FieldMap Map<String, Object> map);
/**
 * 11、取消设备指纹分享给用户
 *     地址：http://52.177.190.126:8081/api/device/cancelShareDevice
 *     POST
 *     Content-Type:application/x-www-form-urlencoded
 *     请求参数	参数类型	是否为空	备注
 *     userId	Integer	N	分享的用户id
 *     deviceId	Integer	N	设备id
 *     响应数据：
 *     {
 *         "code": 200,
 *             "data": {
 *         "bluetoothMac": "289w112-4454545~4545",   //蓝牙mac
 *                 "bluetoothName": "wiki",   //蓝牙名称
 *                 "ctime": 1585389139569,
 *                 "deviceId": 1,   //设备主键id
 *                 "deviceName": "petiyaak",   //设备名称
 *                 "id": 2,
 *                 "isOwner": 1,
 *                 "leftIndex": 13,
 *                 "leftLittle": 16,
 *                 "leftMiddle": 14,
 *                 "leftRing": 15,
 *                 "leftThumb": 12,
 *                 "nickName": "",
 *                 "rightIndex": 23,
 *                 "rightLittle": 26,
 *                 "rightMiddle": 24,
 *                 "rightRing": 25,
 *                 "rightThumb": 22,
 *                 "userId": 1,
 *                 "utime": 1585389139811   //更新时间,时间戳格式
 *     },
 *         "message": "成功"
 *     }
 */
@FormUrlEncoded
@POST("/api/device/cancelShareDevice")
Observable<BaseRespone<LoginRespone>> cancelShareDevice(@FieldMap Map<String, Object> map);

/**
 * 12、根据设备id,获取使用该设备的所有用户信息
 * 地址：http://52.177.190.126:8081/api/device/getUserListByDeviceId
 * POST
 * Content-Type:application/x-www-form-urlencoded
 * 请求参数	参数类型	是否为空	备注
 * deviceId	Integer	N	设备id
 * 响应数据：
 * {
 *   "code": 200,
 *   "data": [
 *     {
 *       "avatar": 0,
 *       "avatarId": 3,
 *       "email": "3794691@qq.com",
 *       "id": 4,
 *       "inTime": "2020-03-23 17:36:12",
 *       "nickname": "rayping",
 *       "phonenumber": "13265091020",
 *       "roleId": 2,
 *       "sex": 1,
 *       "status": 1,
 *       "username": "rayping"
 *     }
 *   ],
 *   "message": "成功"
 * }
 */
@FormUrlEncoded
@POST("/api/device/getUserListByDeviceId")
Observable<BaseRespone<List<UserInfo>>> getUserListByDeviceId(@FieldMap Map<String, Object> map);
}


