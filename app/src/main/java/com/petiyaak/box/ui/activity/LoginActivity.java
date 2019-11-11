package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.presenter.BasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_submit)
    TextView loginSubmit;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoingEvent(int i) {

    }



    @OnClick(R.id.login_submit)
    public void onClick() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
