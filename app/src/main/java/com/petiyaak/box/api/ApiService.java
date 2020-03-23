package com.petiyaak.box.api;


import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.model.respone.LoginRespone;

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
    Observable<BaseRespone<LoginRespone>> login(@FieldMap Map<String, Object> map);

    /**
     *  用户登录接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/api/register")
    Observable<BaseRespone<LoginRespone>> register(@FieldMap Map<String, Object> map);



}
