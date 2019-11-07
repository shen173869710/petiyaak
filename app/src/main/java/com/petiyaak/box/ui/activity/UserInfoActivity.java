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
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.presenter.BasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class UserInfoActivity extends BaseActivity {

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


    private UserInfo userInfo;
    @Override
    protected int getContentView() {
        return R.layout.activity_user_info;
    }


    @Override
    public void initData() {

        userInfo = (UserInfo) getIntent().getSerializableExtra("user");
        mainTitleTitle.setText("Scan Left and Right Finger");
        uesrItemName.setText(userInfo.userName);
        mainTitleBack.setVisibility(View.VISIBLE);
        initFinger();
    }

    private void initFinger() {
        if (userInfo.leftFinger) {
            userLeftTitle.setTextColor(getResources().getColor(R.color.blue));
            userLeftIcon.setBackgroundColor(getResources().getColor(R.color.blue));
            userRightTitle.setTextColor(getResources().getColor(R.color.black_30));
            userRightIcon.setBackgroundColor(getResources().getColor(R.color.black_30));
            showSel(userFinger1, userFinger1Value, userInfo.finger1);
            showSel(userFinger2, userFinger2Value, userInfo.finger2);
            showSel(userFinger3, userFinger3Value, userInfo.finger3);
            showSel(userFinger4, userFinger4Value, userInfo.finger4);
            showSel(userFinger5, userFinger5Value, userInfo.finger5);
        }else {
            userRightTitle.setTextColor(getResources().getColor(R.color.blue));
            userRightIcon.setBackgroundColor(getResources().getColor(R.color.blue));
            userLeftTitle.setTextColor(getResources().getColor(R.color.black_30));
            userLeftIcon.setBackgroundColor(getResources().getColor(R.color.black_30));
            showSel(userFinger1, userFinger1Value, userInfo.finger6);
            showSel(userFinger2, userFinger2Value, userInfo.finger7);
            showSel(userFinger3, userFinger3Value, userInfo.finger8);
            showSel(userFinger4, userFinger4Value, userInfo.finger9);
            showSel(userFinger5, userFinger5Value, userInfo.finger10);
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
                userInfo.leftFinger = true;
                initFinger();
            }
        });

        RxView.clicks(userRight).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                userInfo.leftFinger = false;
                initFinger();
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
}
