package com.petiyaak.box.api;

import android.annotation.SuppressLint;

import com.petiyaak.box.util.LogUtils;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by czl on 2019-7-9.
 */
public class ApiUtil {
    public static String TAG = "Apiutil";
    public static long CONNECT_TIME = 30;
    public static long RADE_TIMEOUR = 30;

    public static  ApiUtil okhttpInstance;

    public static ApiService createApiService() {
        try{
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                @Override
//                public void log(String message) {
//                    LogUtils.e(TAG, "----request   " + message);
//                }
//            });

            //实例化OkHttpClient
            OkHttpClient.Builder okHttpClientBuilder =createOkHttp();
            OkHttpClient okhttpClient = okHttpClientBuilder.addInterceptor(new HttpLogInterceptor()).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://52.177.190.126:8081")
                    .client(okhttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
        }catch (Exception e){
            LogUtils.e("ApiUtil",e.getMessage());
        }

        return null;
    }



    public static ApiService createApiService(String url) {
        try{
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtils.e(TAG, "----request   " + message);
                }
            });

            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //实例化OkHttpClient
            OkHttpClient.Builder okHttpClientBuilder =createOkHttp();
            OkHttpClient client = okHttpClientBuilder.addInterceptor(httpLoggingInterceptor).build();

            //初始化 retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
        }catch (Exception e){
            LogUtils.e("ApiUtil",e.getMessage());
        }

        return null;
    }



    public static OkHttpClient.Builder createOkHttp(){
        //初始化OkHttp
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(RADE_TIMEOUR , TimeUnit.SECONDS);
        okHttpClientBuilder.retryOnConnectionFailure(false);
//        okHttpClientBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
//        okHttpClientBuilder.eventListenerFactory(HttpEventListener.FACTORY);
//        //默认信任所有的证书
//        okHttpClientBuilder.sslSocketFactory(createSSLSocketFactory());
//        okHttpClientBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
        return okHttpClientBuilder;
    }


    /**
     * 默认信任所有的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return sSLSocketFactory;
    }

    public static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
