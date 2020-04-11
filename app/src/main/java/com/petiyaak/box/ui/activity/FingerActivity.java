package com.petiyaak.box.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.customview.FingerDialog;
import com.petiyaak.box.customview.OnDialogClick;
import com.petiyaak.box.event.ConnectEvent;
import com.petiyaak.box.model.bean.FingerInfo;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.FingerPresenter;
import com.petiyaak.box.util.ClientManager;
import com.petiyaak.box.util.ConnectResponse;
import com.petiyaak.box.util.LogUtils;
import com.petiyaak.box.util.NoFastClickUtils;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.IFingerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;
import butterknife.BindView;
import butterknife.OnClick;

public class FingerActivity extends BaseActivity<FingerPresenter> implements IFingerView {
    private final String TAG = "FingerActivity";
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

    @BindView(R.id.main_title_right_image)
    ImageView mainTitleRightImage;

    private FingerInfo fingerInfo;
    PetiyaakBoxInfo info;
    UserInfo userInfo;
    boolean isBind;
    private int postion = 0;

    UUID readUuid;
    UUID writeUuid;
    UUID serverId;
    FingerDialog dialog;

    /**
     *  当前的指纹ID
     */
    private int currentId;

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
    protected FingerPresenter createPresenter() {
        return new FingerPresenter();
    }

    @Override
    public void initData() {
        fingerInfo = new FingerInfo();
        fingerInfo.leftFinger = true;
        mainTitleTitle.setText("Bind Finger");
        uesrItemName.setText(fingerInfo.userName);
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleRight.setVisibility(View.VISIBLE);
        mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);

        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        userInfo = (UserInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_USER);
        isBind = getIntent().getBooleanExtra(ConstantEntiy.INTENT_BIND, false);
        initFinger();

        mPresenter.getFingerprints(userInfo.getId(), info.getDeviceId());
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
        findViewById(R.id.main_title_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                write("ATFDE=999".getBytes());
            }
        });


        findViewById(R.id.main_title_right_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                ToastUtils.showToast("Please wait a few seconds while connecting to bluetooth");
                ClientManager.getInstance().connectDevice(info.getBluetoothMac(), new ConnectResponse() {
                    @Override
                    public void onResponse(boolean isConnect) {
                        if (isConnect) {
                            mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_con);
                        } else {
                            mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);
                        }
                    }
                });
            }
        });

        /**
         *   链接设备
         */
        if (!TextUtils.isEmpty(info.getBluetoothMac())) {
            ClientManager.getInstance().connectDevice(info.getBluetoothMac(), new ConnectResponse() {
                @Override
                public void onResponse(boolean isConnect) {
                    if (isConnect) {
                        mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_con);
                        ClientManager.getInstance().notifyData(info.getBluetoothMac(), new BleNotifyResponse() {

                            @Override
                            public void onResponse(int code) {
                                LogUtils.e(TAG, "notify onResponse code =  " + code);
                            }

                            @Override
                            public void onNotify(UUID service, UUID character, byte[] value) {
                                LogUtils.e(TAG, "notify()  " + new String(value));
                                getRespone(new String(value).trim());
                            }
                        });

                    } else {
                        mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);
                    }
                }
            });
        }


    }
    /**
     *     获取指纹的返回值
     * @param respone
     */
    private void getRespone(String respone) {
        if (TextUtils.isEmpty(respone)) {
            return;
        }
        if (respone.contains(ConstantEntiy.ATFAE)) {
            if (respone.contains(ConstantEntiy.ATFAE_START)) {

            }else if (respone.contains(ConstantEntiy.ATFAE_FAIL1)) {
                ToastUtils.showToast(getResources().getString(R.string.atfae_fail1));
            }else if (respone.contains(ConstantEntiy.ATFAE_FAIL2)) {
                ToastUtils.showToast(getResources().getString(R.string.atfae_fail2));
            }else if (respone.contains(ConstantEntiy.ATFAE_FAIL3)) {
                ToastUtils.showToast(getResources().getString(R.string.atfae_fail3));
            }else if (respone.contains(ConstantEntiy.ATFAE_FAIL4)) {
                ToastUtils.showToast(getResources().getString(R.string.atfae_fail4));
            }else if ((respone.contains(ConstantEntiy.ATFAE_OK))){
//                ATFAE=OK-30/100
//                ATFAE=OK-ID=2
                if (respone.contains("ID") || respone.contains("id")) {
                    String[] ids = respone.split("=");
                    if (ids != null && ids.length == 3) {
                        String id = ids[2];
                        LogUtils.e(TAG, " id  --"+id);
                        id = id.replaceAll("\r|\n","");
                        try {
                            currentId = Integer.valueOf(id.trim());
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            mPresenter.addFingerprints(userInfo.getId(), info.getDeviceId(), postion, currentId, isBind);
                        }catch (Exception e) {
                            LogUtils.e(TAG, " id  异常"+e.getMessage());
                        }
                    }
                }else{
                    String[] ids = respone.split("-");
                    if (ids != null && ids.length == 2) {
                        String str = ids[1].substring(0,2);
                        LogUtils.e(TAG, " 当前进度 --"+str + "   ids[1] ="+ids[1]);
                        try {
                            int progress = Integer.valueOf(str);
                            if (dialog != null) {
                                dialog.setProgress(progress);
                            }
                        }catch (Exception e) {

                        }
                    }
                }
            }
        }

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
        PetiyaakBoxInfo responeData = (PetiyaakBoxInfo) respone.getData();
        if (responeData != null) {
            info = responeData;
            initFinger();
        }
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
    private void showFingerDialog() {
        currentId = 0;
        dialog = new FingerDialog(FingerActivity.this, new OnDialogClick() {
            @Override
            public void onDialogOkClick(String value) {

            }

            @Override
            public void onDialogCloseClick(String value) {

            }
        });

        if (dialog != null) {
            dialog.show();
            /**
             *  开始注册指纹
             */
            write(ConstantEntiy.ATFAE.getBytes());
        }
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
            if (info.getLeftThumb() >= 0) {
                imageView = userFinger1;
                resId = R.mipmap.finger_p;
                textView = userFinger1Id;
                fingerId = info.getLeftThumb();
            } else {
                imageView = userFinger1;
                resId = R.mipmap.finger_n;
                textView = userFinger1Id;
                fingerId = -1;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getLeftIndex() >= 0) {
                imageView = userFinger2;
                resId = R.mipmap.finger_p;
                textView = userFinger2Id;
                fingerId = info.getLeftIndex();
            } else {
                imageView = userFinger2;
                resId = R.mipmap.finger_n;
                textView = userFinger2Id;
                fingerId = -1;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getLeftMiddle() >= 0) {
                imageView = userFinger3;
                resId = R.mipmap.finger_p;
                textView = userFinger3Id;
                fingerId = info.getLeftMiddle();
            } else {
                imageView = userFinger3;
                resId = R.mipmap.finger_n;
                textView = userFinger3Id;
                fingerId = -1;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getLeftRing() >= 0) {
                imageView = userFinger4;
                resId = R.mipmap.finger_p;
                textView = userFinger4Id;
                fingerId = info.getLeftRing();
            } else {
                imageView = userFinger4;
                resId = R.mipmap.finger_n;
                textView = userFinger4Id;
                fingerId = -1;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getLeftLittle() >= 0) {
                imageView = userFinger5;
                resId = R.mipmap.finger_p;
                textView = userFinger5Id;
                fingerId = info.getLeftLittle();
            } else {
                imageView = userFinger5;
                resId = R.mipmap.finger_n;
                textView = userFinger5Id;
                fingerId = -1;
            }
            fingerSel(imageView, resId, textView, fingerId);
        } else {
            if (info.getRightThumb() >= 0) {
                imageView = userFinger1;
                resId = R.mipmap.finger_p;
                textView = userFinger1Id;
                fingerId = info.getRightThumb();
            } else {
                imageView = userFinger1;
                resId = R.mipmap.finger_n;
                textView = userFinger1Id;
                fingerId = -1;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getRightIndex() >= 0) {
                imageView = userFinger2;
                resId = R.mipmap.finger_p;
                textView = userFinger2Id;
                fingerId = info.getRightIndex();
            } else {
                imageView = userFinger2;
                resId = R.mipmap.finger_n;
                textView = userFinger2Id;
                fingerId = -1;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getRightMiddle() >= 0) {
                imageView = userFinger3;
                resId = R.mipmap.finger_p;
                textView = userFinger3Id;
                fingerId = info.getRightMiddle();
            } else {
                imageView = userFinger3;
                resId = R.mipmap.finger_n;
                textView = userFinger3Id;
                fingerId = -1;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getRightRing() >= 0) {
                imageView = userFinger4;
                resId = R.mipmap.finger_p;
                textView = userFinger4Id;
                fingerId = info.getRightRing();
            } else {
                imageView = userFinger4;
                resId = R.mipmap.finger_n;
                textView = userFinger4Id;
                fingerId = -1;
            }
            fingerSel(imageView, resId, textView, fingerId);
            if (info.getRightLittle() >= 0) {
                imageView = userFinger5;
                resId = R.mipmap.finger_p;
                textView = userFinger5Id;
                fingerId = info.getRightLittle();
            } else {
                imageView = userFinger5;
                resId = R.mipmap.finger_n;
                textView = userFinger5Id;
                fingerId = -1;
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
        if (fingerId >= 0) {
            textView.setText("" + fingerId);
        } else {
            textView.setText("");
        }
        imageView.setBackgroundResource(resId);
    }


    @OnClick({R.id.main_title_back, R.id.user_left, R.id.user_right,
            R.id.user_finger_1, R.id.user_finger_2, R.id.user_finger_3,
            R.id.user_finger_4, R.id.user_finger_5})
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


    public void write(byte[] value) {
        ClientManager.getInstance().writeData(info.getBluetoothMac(), value, new BleWriteResponse() {
            @Override
            public void onResponse(int code) {
                LogUtils.e(TAG, "writeData -- onResponse code= "+code);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectEvent(ConnectEvent event) {
        if (event.isConnect()) {
            mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_con);
        }else {
            mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (info != null) {
            ClientManager.getInstance().unnotifyData(info.getBluetoothMac(), new BleUnnotifyResponse() {
                @Override
                public void onResponse(int code) {
                    LogUtils.e(TAG, "unnotifyData -- onResponse code= "+code);
                }
            });
            ClientManager.getInstance().disconnect(info.getBluetoothMac());
        }
    }
}
