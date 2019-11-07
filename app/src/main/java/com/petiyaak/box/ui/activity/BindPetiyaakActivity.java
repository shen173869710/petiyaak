package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.content.Intent;

import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.presenter.BasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class BindPetiyaakActivity extends BaseActivity {




    @Override
    protected int getContentView() {
        return R.layout.activity_bind_petiyaak;
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

}
