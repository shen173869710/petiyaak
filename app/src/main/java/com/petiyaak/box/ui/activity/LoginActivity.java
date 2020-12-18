package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.customview.MClearEditText;
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.LoginPresenter;
import com.petiyaak.box.util.NoFastClickUtils;
import com.petiyaak.box.util.SpUtils;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.ILoginView;

import butterknife.BindView;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class LoginActivity extends BaseActivity <LoginPresenter> implements ILoginView {


    @BindView(R.id.login_submit)
    TextView loginSubmit;
    @BindView(R.id.login_register)
    TextView loginRegister;

    @BindView(R.id.login_name)
    MClearEditText loginName;

    @BindView(R.id.login_pwd)
    MClearEditText loginPwd;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }


    @Override
    public void initData() {
        String account = SpUtils.getInstance().getString(SpUtils.SAVE_ACCOUNT);
        String pwd = SpUtils.getInstance().getString(SpUtils.SAVE_PWD);

        if (!TextUtils.isEmpty(account)) {
            loginName.setText(account);
        }

        if (!TextUtils.isEmpty(pwd)) {
            loginPwd.setText(pwd);
        }

    }

    @Override
    public void initListener() {
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                String name = loginName.getText().toString().trim();
                String pwd = loginPwd.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showToast(getString(R.string.empty_name));
                    return;
                }

                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showToast(getString(R.string.empty_pwd));
                    return;
                }

                mPresenter.doLogin(name, pwd);
            }
        });

        loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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
        BaseApp.userInfo = (UserInfo) respone.data;
        if (BaseApp.userInfo != null) {

            SpUtils.getInstance().putString(SpUtils.SAVE_ACCOUNT, loginName.getText().toString().trim());
            SpUtils.getInstance().putString(SpUtils.SAVE_PWD, loginPwd.getText().toString().trim());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else {
            ToastUtils.showToast("登陆失败");
        }

    }

    @Override
    public void loginFail(Throwable error, Integer code, String msg) {
        ToastUtils.showToast(msg+"");

        BaseApp.userInfo = new UserInfo();
        BaseApp.userInfo.setId(1);
        BaseApp.userInfo.setAvatar("1.png");
        BaseApp.userInfo.setUsername("123");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void registerSuccess(BaseRespone respone) {

    }

    @Override
    public void registerFail(Throwable error, Integer code, String msg) {

    }

}
