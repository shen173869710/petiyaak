package com.petiyaak.box.presenter;



import com.petiyaak.box.api.ApiService;
import com.petiyaak.box.api.ApiUtil;
import com.petiyaak.box.api.HttpManager;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.BaseView;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends BaseView> implements Presenter<T> {


    private T mBaseView;
    private ApiService apiService;
    private HttpManager httpManager;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(T baseView) {
        mBaseView = baseView;
        apiService = ApiUtil.createApiService();
        httpManager = new HttpManager();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        mBaseView = null;
        mCompositeDisposable.dispose();
    }

    @Override
    public void destroy() {

    }


    public T getBaseView() {
        return mBaseView;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public CompositeDisposable getmCompositeDisposable() {
        return mCompositeDisposable;
    }

    public HttpManager getHttpManager() {
        return httpManager;
    }

    public void doHttpTask(Observable observable, final HttpManager.OnResultListener onResultListener) {
       httpManager.doHttpTaskWithDialog(getBaseView(), observable,mCompositeDisposable,onResultListener);

    }

    public void doHttpTaskWihtDialog(Observable observable, final HttpManager.OnResultListener onResultListener) {
//        if (BaseApp.getInstance().isConnectNomarl()) {
            httpManager.doHttpTask(getBaseView(), observable,mCompositeDisposable,onResultListener);
//        }else {
//            String error = "网络已经断开,请检查你的网络";
//            if (onResultListener != null) {
//                onResultListener.onError(null,500, error);
//            }
//            ToastUtils.showToast(error);
//        }
    }
}

