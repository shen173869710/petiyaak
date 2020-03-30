package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.petiyaak.box.customview.FingerDialog;
import com.petiyaak.box.customview.OnDialogClick;
import com.petiyaak.box.model.bean.FingerInfo;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.FingerPresenter;
import com.petiyaak.box.util.NoFastClickUtils;
import com.petiyaak.box.view.IFingerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class FingerActivity extends BaseActivity<FingerPresenter> implements IFingerView {
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
    @BindView(R.id.user_finger_1_id)
    TextView userFinger1Id;

    @BindView(R.id.user_finger_2)
    ImageView userFinger2;
    @BindView(R.id.user_finger_2_value)
    TextView userFinger2Value;
    @BindView(R.id.user_finger_2_id)
    TextView userFinger2Id;

    @BindView(R.id.user_finger_3)
    ImageView userFinger3;
    @BindView(R.id.user_finger_3_value)
    TextView userFinger3Value;
    @BindView(R.id.user_finger_3_id)
    TextView userFinger3Id;

    @BindView(R.id.user_finger_4)
    ImageView userFinger4;
    @BindView(R.id.user_finger_4_value)
    TextView userFinger4Value;
    @BindView(R.id.user_finger_4_id)
    TextView userFinger4Id;

    @BindView(R.id.user_finger_5)
    ImageView userFinger5;
    @BindView(R.id.user_finger_5_value)
    TextView userFinger5Value;
    @BindView(R.id.user_finger_5_id)
    TextView userFinger5Id;

    @BindView(R.id.share_submit)
    TextView shareSubmit;
    private FingerInfo fingerInfo;
    PetiyaakBoxInfo info;
    UserInfo userInfo;
    boolean isBind;
    private int postion = 0;


    public static Intent startIntent(Context content, PetiyaakBoxInfo box, UserInfo info, boolean isBind) {
        Intent intent = new Intent(content, FingerActivity.class);
        intent.putExtra(ConstantEntiy.INTENT_BOX, box);
        intent.putExtra(ConstantEntiy.INTENT_USER, info);
        intent.putExtra(ConstantEntiy.INTENT_BIND, isBind);
        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_finger;
    }


    @Override
    public void initData() {
        fingerInfo = new FingerInfo();
        fingerInfo.leftFinger = true;
        mainTitleTitle.setText("Scan Finger");
        uesrItemName.setText(fingerInfo.userName);
        mainTitleBack.setVisibility(View.VISIBLE);

        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        userInfo = (UserInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_USER);
        isBind = getIntent().getBooleanExtra(ConstantEntiy.INTENT_BIND, false);
        initFinger();

        if (isBind) {
            shareSubmit.setText("Bind Finger");
        }
        mPresenter.getFingerprints(userInfo.getId(), 2);
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
        } else {
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
        initId();
    }

    @Override
    public void initListener() {

    }

    @Override
    protected FingerPresenter createPresenter() {
        return new FingerPresenter();
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    public void showSel(ImageView finger, TextView value, String fingerCode) {
        boolean isSel = false;
        if (!TextUtils.isEmpty(fingerCode)) {
            isSel = true;
        }
        if (isSel) {
            finger.setBackgroundResource(R.mipmap.finger_p);
            value.setTextColor(getResources().getColor(R.color.blue));
        } else {
            finger.setBackgroundResource(R.mipmap.finger_n);
            value.setTextColor(getResources().getColor(R.color.black_30));
        }
    }

    @Override
    public void addFingerSuccess(BaseRespone respone) {

    }

    @Override
    public void addFingerFail(Throwable error, Integer code, String msg) {

    }

    @Override
    public void getFingerSuccess(BaseRespone respone) {
        PetiyaakBoxInfo responeData = (PetiyaakBoxInfo) respone.getData();
        if (responeData != null) {
            info = responeData;
            initFinger();
        }
    }

    @Override
    public void getFingerFail(Throwable error, Integer code, String msg) {

    }

    @Override
    public void shareSuccess(BaseRespone respone) {

    }

    @Override
    public void shareFail(Throwable error, Integer code, String msg) {

    }


    private void showFingerDialog() {
        new FingerDialog(FingerActivity.this, new OnDialogClick() {
            @Override
            public void onDialogOkClick(String value) {
                mPresenter.addFingerprints(userInfo.getId(), 2, postion, 100+postion, 2);;
            }

            @Override
            public void onDialogCloseClick(String value) {

            }
        }).show();
    }

    /**
     * 初始化用户ID
     */
    public void initId() {
        ImageView imageView = null;
        int resId = 0;
        TextView textView = null;
        int fingerId = 0;

        if (fingerInfo.leftFinger) {
            if (info.getLeftThumb() > 0) {
                imageView = userFinger1;
                resId = R.mipmap.finger_p;
                textView = userFinger1Id;
                fingerId = info.getLeftThumb();
            } else {
                imageView = userFinger1;
                resId = R.mipmap.finger_n;
                textView = userFinger1Id;
                fingerId = 0;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getLeftIndex() > 0) {
                imageView = userFinger2;
                resId = R.mipmap.finger_p;
                textView = userFinger2Id;
                fingerId = info.getLeftIndex();
            } else {
                imageView = userFinger2;
                resId = R.mipmap.finger_n;
                textView = userFinger2Id;
                fingerId = 0;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getLeftMiddle() > 0) {
                imageView = userFinger3;
                resId = R.mipmap.finger_p;
                textView = userFinger3Id;
                fingerId = info.getLeftMiddle();
            } else {
                imageView = userFinger3;
                resId = R.mipmap.finger_n;
                textView = userFinger3Id;
                fingerId = 0;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getLeftRing() > 0) {
                imageView = userFinger4;
                resId = R.mipmap.finger_p;
                textView = userFinger4Id;
                fingerId = info.getLeftRing();
            } else {
                imageView = userFinger4;
                resId = R.mipmap.finger_n;
                textView = userFinger4Id;
                fingerId = 0;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getLeftLittle() > 0) {
                imageView = userFinger5;
                resId = R.mipmap.finger_p;
                textView = userFinger5Id;
                fingerId = info.getLeftLittle();
            } else {
                imageView = userFinger5;
                resId = R.mipmap.finger_n;
                textView = userFinger5Id;
                fingerId = 0;
            }
            fingerSel(imageView, resId, textView, fingerId);
        } else {
            if (info.getRightThumb() > 0) {
                imageView = userFinger1;
                resId = R.mipmap.finger_p;
                textView = userFinger1Id;
                fingerId = info.getRightThumb();
            } else {
                imageView = userFinger1;
                resId = R.mipmap.finger_n;
                textView = userFinger1Id;
                fingerId = 0;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getRightIndex() > 0) {
                imageView = userFinger2;
                resId = R.mipmap.finger_p;
                textView = userFinger2Id;
                fingerId = info.getRightIndex();
            } else {
                imageView = userFinger2;
                resId = R.mipmap.finger_n;
                textView = userFinger2Id;
                fingerId = 0;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getRightMiddle() > 0) {
                imageView = userFinger3;
                resId = R.mipmap.finger_p;
                textView = userFinger3Id;
                fingerId = info.getRightMiddle();
            } else {
                imageView = userFinger3;
                resId = R.mipmap.finger_n;
                textView = userFinger3Id;
                fingerId = 0;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getRightRing() > 0) {
                imageView = userFinger4;
                resId = R.mipmap.finger_p;
                textView = userFinger4Id;
                fingerId = info.getRightRing();
            } else {
                imageView = userFinger4;
                resId = R.mipmap.finger_n;
                textView = userFinger4Id;
                fingerId = 0;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getRightLittle() > 0) {
                imageView = userFinger5;
                resId = R.mipmap.finger_p;
                textView = userFinger5Id;
                fingerId = info.getRightLittle();
            } else {
                imageView = userFinger5;
                resId = R.mipmap.finger_n;
                textView = userFinger5Id;
                fingerId = 0;
            }
            fingerSel(imageView, resId, textView, fingerId);
        }
    }

    /**
     * 如果有指纹ID
     *
     * @param imageView
     * @param resId
     * @param textView
     * @param fingerId
     */
    public void fingerSel(ImageView imageView, int resId, TextView textView, int fingerId) {
        if (fingerId > 0) {
            textView.setText("" + fingerId);
        } else {
            textView.setText("");
        }
        imageView.setBackgroundResource(resId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.main_title_back,R.id.user_left, R.id.user_right,
            R.id.user_finger_1, R.id.user_finger_2, R.id.user_finger_3,
            R.id.user_finger_4, R.id.user_finger_5, R.id.share_submit})
    public void onClick(View view) {

        if (NoFastClickUtils.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.main_title_back:
                finish();
                break;
            case R.id.user_left:
                fingerInfo.leftFinger = true;
                initFinger();
                break;
            case R.id.user_right:
                fingerInfo.leftFinger = false;
                initFinger();
                break;
            case R.id.share_submit:
                if (isBind) {
                    mPresenter.addFingerprints(userInfo.getId(), 2, postion, postion, 1);
                } else {
                    mPresenter.shareToUser(userInfo.getId(), 2);
                }
                break;

            case R.id.user_finger_1:
                if (fingerInfo.leftFinger) {
                    postion = 1;
                } else {
                    postion = 6;
                }
                showFingerDialog();
                break;
            case R.id.user_finger_2:
                if (fingerInfo.leftFinger) {
                    postion = 2;
                } else {
                    postion = 7;
                }
                showFingerDialog();
                break;
            case R.id.user_finger_3:
                if (fingerInfo.leftFinger) {
                    postion = 3;
                } else {
                    postion = 8;
                }
                showFingerDialog();
                break;
            case R.id.user_finger_4:
                if (fingerInfo.leftFinger) {
                    postion = 4;
                } else {
                    postion = 9;
                }
                showFingerDialog();
                break;
            case R.id.user_finger_5:
                if (fingerInfo.leftFinger) {
                    postion = 5;
                } else {
                    postion = 10;
                }
                showFingerDialog();
                break;
        }
    }
}
