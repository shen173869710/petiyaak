package com.petiyaak.box.base;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.petiyaak.box.R;
import com.petiyaak.box.customview.LoadingDialog;
import com.petiyaak.box.presenter.BasePresenter;
import com.petiyaak.box.view.BaseView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;


/**
 * 基类Fragment
 * 备注:所有的Fragment都继承自此Fragment
 * 1.规范团队开发
 * 2.统一处理Fragment所需配置,初始化
 *
 * @author czl
 */
public abstract class BaseFragment<T extends BasePresenter> extends RxFragment implements BaseView, View.OnTouchListener {

    protected T mPresenter;
    protected Context mContext;
    protected View mView;
    protected LoadingDialog mLoadingDialog;
    protected Unbinder bind;

    private boolean isFirst = true;//是否第一次加载
    private boolean isViewCreate = false;//view是否创建
    private boolean isViewVisible = false;//view是否可见

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = getContentView();
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        bind = ButterKnife.bind(this, mView);
        initView();
        initData();
        initListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreate = true;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisible = isVisibleToUser;
        if (isVisibleToUser && isViewCreate) {
            visibleToUser();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isViewVisible) {
            visibleToUser();
        }
    }

    /**
     * 懒加载
     * 让用户可见
     * 第一次加载
     */
    public abstract void firstLoad();

    /**
     * 懒加载
     * 让用户可见
     */
    protected void visibleToUser() {
        if (isFirst) {
            firstLoad();
            isFirst = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView.getParent() != null) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
        mView.setOnTouchListener(this);
        return mView;
    }

    protected abstract T createPresenter();

    @Override
    public LifecycleTransformer bindLifecycle() {
        LifecycleTransformer objectLifecycleTransformer = bindToLifecycle();
        return objectLifecycleTransformer;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        bind.unbind();
        isViewCreate = false;
    }

    /**
     * 是否已经创建
     *
     * @return
     */
    public boolean isCreated() {
        return mView != null;
    }

    /**
     * 获取显示view
     */
    protected abstract View getContentView();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化Data
     */
    protected abstract void initData();

    public abstract void initListener();

    /**
     * 显示等待提示框
     */
    public Dialog showWaitingDialog(String tip) {
        mLoadingDialog = new LoadingDialog(mContext, R.style.CustomDialog);
        return mLoadingDialog;
    }

    @Override
    public void dismissDialog() {
        hideWaitingDialog();
    }

    /**
     * 隐藏等待提示框
     */
    public void hideWaitingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }


    @Override
    public void showDialog() {
        if (null == mLoadingDialog) {
            showWaitingDialog("");
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;//消费掉点击事件,防止跑到下一层去
    }



}
