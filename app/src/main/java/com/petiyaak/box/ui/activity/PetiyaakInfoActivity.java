package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.presenter.BasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class PetiyaakInfoActivity extends BaseActivity {
    private  final int REQUEST_CODE_OPEN_GPS = 1;
    private  final int REQUEST_CODE_PERMISSION_LOCATION = 2;

    @BindView(R.id.main_title_back)
    RelativeLayout mainTitleBack;
    @BindView(R.id.main_title_title)
    TextView mainTitleTitle;
    @BindView(R.id.main_title_right)
    RelativeLayout mainTitleRight;
    @BindView(R.id.info_icon)
    CircleImageView infoIcon;
    @BindView(R.id.info_setting)
    LinearLayout infoSetting;
    @BindView(R.id.info_next)
    LinearLayout infoNext;
    @BindView(R.id.info_finger)
    LinearLayout infoFinger;


    @Override
    protected int getContentView() {
        return R.layout.activity_petiyaak_info;
    }


    @Override
    public void initData() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleTitle.setText("My petiyaakInfo Box");
    }

    @Override
    public void initListener() {
        RxView.clicks(mainTitleBack).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });

        RxView.clicks(infoNext).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                startActivity(new Intent(PetiyaakInfoActivity.this, BindPetiyaakActivity.class));
            }
        });

        RxView.clicks(infoFinger).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                startActivity(new Intent(PetiyaakInfoActivity.this, BindFingerActivity.class));
            }
        });
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


    @Override
    protected void onResume() {
        super.onResume();
    }
}
