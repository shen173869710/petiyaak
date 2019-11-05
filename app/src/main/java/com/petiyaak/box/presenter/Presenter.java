package com.petiyaak.box.presenter;


import com.petiyaak.box.view.BaseView;

public interface Presenter<V extends BaseView> {
    void attachView(V mvpView);
    void detachView();
    void destroy();
}
