package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.event.ShareSucessEvent;
import com.petiyaak.box.model.bean.FingerInfo;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.SharePresenter;
import com.petiyaak.box.util.NoFastClickUtils;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.IShareView;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class FingerActivity extends BaseActivity <SharePresenter> implements IShareView {
    @BindView(R.id.main_title_back)
    RelativeLayout mainTitleBack;
    @BindView(R.id.main_title_title)
    TextView mainTitleTitle;
    @BindView(R.id.main_title_right)
    RelativeLayout mainTitleRight;
    @BindView(R.id.uesr_item_name)
    TextView uesrItemName;
    @BindView(R.id.user_left_title)
    TextView userLeftTitle;
    @BindView(R.id.user_left_icon)
    View userLeftIcon;
    @BindView(R.id.user_left)
    LinearLayout userLeft;
    @BindView(R.id.user_right_title)
    TextView userRightTitle;
    @BindView(R.id.user_right_icon)
    View userRightIcon;
    @BindView(R.id.user_right)
    LinearLayout userRight;
    @BindView(R.id.user_finger_1)
    ImageView userFinger1;
    @BindView(R.id.user_finger_1_value)
    TextView userFinger1Value;
    @BindView(R.id.user_finger_2)
    ImageView userFinger2;
    @BindView(R.id.user_finger_2_value)
    TextView userFinger2Value;
    @BindView(R.id.user_finger_3)
    ImageView userFinger3;
    @BindView(R.id.user_finger_3_value)
    TextView userFinger3Value;
    @BindView(R.id.user_finger_4)
    ImageView userFinger4;
    @BindView(R.id.user_finger_4_value)
    TextView userFinger4Value;
    @BindView(R.id.user_finger_5)
    ImageView userFinger5;
    @BindView(R.id.user_finger_5_value)
    TextView userFinger5Value;

    @BindView(R.id.share_submit)
    TextView shareSubmit;
    private FingerInfo fingerInfo;
    PetiyaakBoxInfo info;
    UserInfo userInfo;
    boolean isBind;


    private int postion = 0;
    private String fingerid;

    @Override
    protected int getContentView() {
        return R.layout.activity_finger;
    }


    @Override
    public void initData() {
        fingerInfo = new FingerInfo();
        mainTitleTitle.setText("Scan Left and Right Finger");
        uesrItemName.setText(fingerInfo.userName);
        mainTitleBack.setVisibility(View.VISIBLE);

        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        userInfo = (UserInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_USER);
        isBind = getIntent().getBooleanExtra(ConstantEntiy.INTENT_BIND,false);
        initFinger();

        if (isBind) {
            shareSubmit.setText("Bind Finger");
        }
    }

    private void initFinger() {
        if (fingerInfo.leftFinger) {
            userLeftTitle.setTextColor(getResources().getColor(R.color.blue));
            userLeftIcon.setBackgroundColor(getResources().getColor(R.color.blue));
            userRightTitle.setTextColor(getResources().getColor(R.color.black_30));
            userRightIcon.setBackgroundColor(getResources().getColor(R.color.black_30));
            showSel(userFinger1, userFinger1Value, fingerInfo.finger1);
            showSel(userFinger2, userFinger2Value, fingerInfo.finger2);
            showSel(userFinger3, userFinger3Value, fingerInfo.finger3);
            showSel(userFinger4, userFinger4Value, fingerInfo.finger4);
            showSel(userFinger5, userFinger5Value, fingerInfo.finger5);
        }else {
            userRightTitle.setTextColor(getResources().getColor(R.color.blue));
            userRightIcon.setBackgroundColor(getResources().getColor(R.color.blue));
            userLeftTitle.setTextColor(getResources().getColor(R.color.black_30));
            userLeftIcon.setBackgroundColor(getResources().getColor(R.color.black_30));
            showSel(userFinger1, userFinger1Value, fingerInfo.finger6);
            showSel(userFinger2, userFinger2Value, fingerInfo.finger7);
            showSel(userFinger3, userFinger3Value, fingerInfo.finger8);
            showSel(userFinger4, userFinger4Value, fingerInfo.finger9);
            showSel(userFinger5, userFinger5Value, fingerInfo.finger10);
        }
    }

    @Override
    public void initListener() {
        mainTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RxView.clicks(userLeft).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                fingerInfo.leftFinger = true;
                initFinger();
            }
        });

        RxView.clicks(userRight).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                fingerInfo.leftFinger = false;
                initFinger();
            }
        });

        shareSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                if (isBind) {
                    mPresenter.addFingerprints(userInfo.getId(), 2,postion, postion,1);
                }else {
                    mPresenter.shareToUser(userInfo.getId(), 2);
                }

            }
        });


        userFinger1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }

                if (fingerInfo.leftFinger) {
                    postion = 1;
                    fingerid = fingerInfo.finger1;
                }else {
                    postion = 6;
                }
            }
        });

        userFinger2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                if (fingerInfo.leftFinger) {
                    postion = 2;
                }else {
                    postion = 7;
                }
            }
        });

        userFinger3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                if (fingerInfo.leftFinger) {
                    postion = 3;
                }else {
                    postion = 8;
                }
            }
        });

        userFinger4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                if (fingerInfo.leftFinger) {
                    postion = 4;
                }else {
                    postion = 9;
                }
            }
        });

        userFinger5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                if (fingerInfo.leftFinger) {
                    postion = 5;
                }else {
                    postion = 10;
                }
            }
        });
    }

    @Override
    protected SharePresenter createPresenter() {
        return new SharePresenter();
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    public void showSel (ImageView finger, TextView value, String fingerCode) {
        boolean isSel = false;
        if (!TextUtils.isEmpty(fingerCode)) {
            isSel = true;
        }
        if (isSel) {
            finger.setBackgroundResource(R.mipmap.finger_p);
            value.setTextColor(getResources().getColor(R.color.blue));
        }else {
            finger.setBackgroundResource(R.mipmap.finger_n);
            value.setTextColor(getResources().getColor(R.color.black_30));
        }
    }

    @Override
    public void success(BaseRespone respone) {
        PetiyaakBoxInfo petiyaakBoxInfo = (PetiyaakBoxInfo)respone.getData();
        if (petiyaakBoxInfo != null) {
            ToastUtils.showToast("share success");
            EventBus.getDefault().post(new ShareSucessEvent());
            finish();
        }else {
            ToastUtils.showToast("share faile");
        }
    }

    @Override
    public void fail(Throwable error, Integer code, String msg) {
        ToastUtils.showToast("share faile "+msg);
    }
}
