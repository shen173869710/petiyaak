package com.petiyaak.box.api;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.util.LogUtils;
import com.petiyaak.box.view.BaseView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by czl on 2019/9/7.
 */

public class HttpManager {
    public static final String TAG = "HttpManager";

    /**
     *    有loading 的请求
     * @param baseView
     * @param observable
     * @param onResultListener
     * @param compositeDisposable
     */
    public void doHttpTaskWithDialog(final BaseView baseView, Observable observable,
                                     CompositeDisposable compositeDisposable, final OnResultListener onResultListener) {
        if (baseView != null) {
            baseView.showDialog();
        }

        DisposableObserver disposableObserver = new DisposableObserver() {
            @Override
            public void onNext( Object obj) {
                LogUtils.e(TAG, "response ======" + new Gson().toJson(obj));
                if (baseView != null) {
                    baseView.dismissDialog();
                    BaseRespone respone =  (BaseRespone) obj;
                    if(null == respone) {
                        onResultListener.onError(new Exception(),500, "网络开小差,请重试");
                    }else{
                        if(respone.isOk()){
                            onResultListener.onSuccess(respone);
                        }else if (respone.getCode() == 401){

                        }else {
                            String message = respone.getMessage();
                            if(TextUtils.isEmpty(message)){
                                message = "网络开小差,请重试";
                            }
                            onResultListener.onError(new Exception(),respone.getCode(), message);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "onError===" + e.toString());
                if (baseView != null) {
                    baseView.dismissDialog();
                    onResultListener.onError(e, 500,"网络开小差,请重试");
                }
            }

            @Override
            public void onComplete() {
                if (baseView != null) {
                    baseView.dismissDialog();
                }
                LogUtils.e(TAG, "onComplete()==");
            }
        };

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(baseView.bindLifecycle())
                .subscribeWith(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    /**
     *         无loading 的请求
     * @param baseView
     * @param observable
     * @param onResultListener
     * @param compositeDisposable
     */
    public static void  doHttpTask(final BaseView baseView, Observable observable, CompositeDisposable compositeDisposable,
                                   final OnResultListener onResultListener) {
        DisposableObserver disposableObserver = new DisposableObserver() {
            @Override
            public void onNext(Object obj) {
                LogUtils.e(TAG, "response ======" + new Gson().toJson(obj));
                if (baseView != null) {
                    baseView.dismissDialog();
                    BaseRespone respone =  (BaseRespone) obj;
                    if(null == respone) {
                        onResultListener.onError(new Exception() ,500, "网络开小差,请重试");
                    }else{
                        if(respone.isOk()){
                            onResultListener.onSuccess(respone);
                        }else{
                            String message = respone.getMessage();
                            if(TextUtils.isEmpty(message)){
                                message = "网络开小差,请重试";
                            }
                            onResultListener.onError(new Exception() , respone.getCode(), message);
                        }
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "onError===" + e.toString());
                if (baseView != null) {
                    onResultListener.onError(e, 500,"网络开小差,请重试");
                }
            }
            @Override
            public void onComplete() {
                if (baseView != null) {
                }
                LogUtils.e(TAG, "onComplete()==");
            }
        };

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .onErrorResumeNext(funcException)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(baseView.bindLifecycle())
                .subscribeWith(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    /**
     *
     * @param observable
     * @param onResultListener
     */
    public static void  doHttpTask(Observable observable, final OnResultListener onResultListener) {
        DisposableObserver disposableObserver = new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                LogUtils.e(TAG, "onNext ======" + o.toString());
                    BaseRespone respone =  (BaseRespone) o;
                    if (respone != null && respone.isOk()) {
                        onResultListener.onSuccess(respone);
                    }else {
                        String message = respone.getMessage();
                        if(TextUtils.isEmpty(message)){
                            message = "网络开小差,请重试";
                        }
                        onResultListener.onError(new Exception() ,500, message);
                    }
            }
            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "onError===" + e.toString());
                    onResultListener.onError(e, 500,"网络开小差,请重试");
            }
            @Override
            public void onComplete() {
                LogUtils.e(TAG, "onComplete()==");
            }
        };

        observable.retryWhen(new RetryWhenNetworkException())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .onErrorResumeNext(funcException)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableObserver);
    }


    /**
     * 无延时
     *
     */
    public static void  doHttpTaskNoDelay(Observable observable, final OnResultListener onResultListener) {
        DisposableObserver disposableObserver = new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                LogUtils.e(TAG, "onNext ======" + o.toString());
                BaseRespone respone =  (BaseRespone) o;
                if (respone != null && respone.isOk()) {
                    onResultListener.onSuccess(respone);
                }else {
                    String message = respone.getMessage();
                    if(TextUtils.isEmpty(message)){
                        message = "网络开小差,请重试";
                    }
                    onResultListener.onError(new Exception() ,500, message);
                }
            }
            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "onError===" + e.toString());
                onResultListener.onError(e, 500,"网络开小差,请重试");
            }
            @Override
            public void onComplete() {
                LogUtils.e(TAG, "onComplete()==");
            }
        };

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .onErrorResumeNext(funcException)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableObserver);
    }

    //接口回调
    public interface OnResultListener {
        void onSuccess(BaseRespone t);
        void onError(Throwable error, Integer code, String msg);
    }

    /**
     * 异常处理
     */
    static Function funcException = new Function<Throwable, Observable>() {
        @Override
        public Observable apply(Throwable throwable) throws Exception {
            return Observable.error(FactoryException.analysisExcetpion(throwable));
        }
    };
}
