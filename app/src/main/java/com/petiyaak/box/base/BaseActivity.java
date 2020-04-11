package com.petiyaak.box.base;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import com.petiyaak.box.R;
import com.petiyaak.box.customview.LoadingDialog;
import com.petiyaak.box.presenter.BasePresenter;
import com.petiyaak.box.util.LogUtils;
import com.petiyaak.box.view.BaseView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Consumer;


public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    protected T mPresenter;
    private LoadingDialog mLoadingDailog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        EventBus.getDefault().register(this);
        BaseApp.activities.add(this);
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        requestPermissions();
        initData();
        initListener();
    }



    protected abstract int getContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        EventBus.getDefault().unregister(this);
    }


    public abstract void initData();

    public abstract void initListener();

    protected abstract T createPresenter();

    /**
     * 显示等待提示框
     */
    private Dialog showWaitingDialog(String tip) {
        mLoadingDailog = new LoadingDialog(this, R.style.CustomDialog);
        return mLoadingDailog;
    }


    @Override
    public LifecycleTransformer bindLifecycle() {
        LifecycleTransformer objectLifecycleTransformer = bindToLifecycle();
        return objectLifecycleTransformer;
    }

    @Override
    public void showDialog() {
        if (null == mLoadingDailog) {
            showWaitingDialog("");
        }
        if (!mLoadingDailog.isShowing()) {
            mLoadingDailog.show();
        }
    }

    @Override
    public void dismissDialog() {
        hideWaitingDialog();
    }

    /**
     * 隐藏等待提示框
     */
    private void hideWaitingDialog() {
        if (mLoadingDailog != null && mLoadingDailog.isShowing()) {
            mLoadingDailog.dismiss();
            mLoadingDailog = null;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions rxPermission = new RxPermissions(this);
            rxPermission
                    .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                // 用户已经同意该权限
                                LogUtils.e("BaseApp", permission.name + " is granted.");
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                LogUtils.e("BaseApp",  permission.name + " is denied. More info should be provided.");
                            } else {
                                // 用户拒绝了该权限，并且选中『不再询问』
                                LogUtils.e("BaseApp",  permission.name + " is denied.");
                            }
                        }
                    });
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoingEvent(int i) {

    }

}
