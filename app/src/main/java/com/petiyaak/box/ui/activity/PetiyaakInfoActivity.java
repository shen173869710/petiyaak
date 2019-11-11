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
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
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
    @BindView(R.id.petiyaak_info_name)
    TextView petiyaakInfoName;
    @BindView(R.id.petiyaak_info_status)
    TextView petiyaakInfoStatus;

    private PetiyaakBoxInfo info;
    @Override
    protected int getContentView() {
        return R.layout.activity_petiyaak_info;
    }

    @Override
    public void initData() {
        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleTitle.setText("My petiyaakInfo Box");
        petiyaakInfoName.setText(info.getItemUserName());
        if (info.isItemBlueStatus()) {
            petiyaakInfoStatus.setText(R.string.connect);
            petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.dark));
        }else {
            petiyaakInfoStatus.setText(R.string.disconnect);
            petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.black));
        }

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
                Intent intent = new Intent(PetiyaakInfoActivity.this, BindPetiyaakActivity.class);
                intent.putExtra(ConstantEntiy.INTENT_BOX, info);
                startActivity(intent);
            }
        });

        RxView.clicks(infoFinger).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                startActivity(new Intent(PetiyaakInfoActivity.this, BindFingerActivity.class));
            }
        });

        RxView.clicks(infoSetting).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Intent intent = new Intent(PetiyaakInfoActivity.this, SettingActivity.class);
                intent.putExtra(ConstantEntiy.INTENT_BOX, info);
                startActivity(intent);
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
    public void onPetiyaakBoxInfo(PetiyaakBoxInfo event) {
        if (event != null) {
            info.setItemBlueStatus(event.isItemBlueStatus());
            info.setItemBlueName(event.getItemBlueName());
            initData();
        }
    }
}
