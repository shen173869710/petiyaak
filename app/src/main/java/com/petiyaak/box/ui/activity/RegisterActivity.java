package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.customview.MClearEditText;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.LoginPresenter;
import com.petiyaak.box.util.NoFastClickUtils;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.ILoginView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


public class RegisterActivity extends BaseActivity <LoginPresenter> implements ILoginView {

    @BindView(R.id.register_submit)
    TextView registerSubmit;

    @BindView(R.id.register_name)
    MClearEditText registerName;

    @BindView(R.id.register_pwd)
    MClearEditText registerPwd;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {


        registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                String name = registerName.getText().toString().trim();
                String pwd = registerPwd.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showToast(getString(R.string.empty_name));
                    return;
                }

                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showToast(getString(R.string.empty_pwd));
                    return;
                }
                mPresenter.doRegister(name, pwd);
            }
        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    public void loginSuccess(BaseRespone respone) {

    }

    @Override
    public void loginFail(Throwable error, Integer code, String msg) {
        ToastUtils.showToast(msg+"");
    }

    @Override
    public void registerSuccess(BaseRespone respone) {
        ToastUtils.showToast("register success");
        finish();
    }

    @Override
    public void registerFail(Throwable error, Integer code, String msg) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoingEvent(int i) {

    }
}
