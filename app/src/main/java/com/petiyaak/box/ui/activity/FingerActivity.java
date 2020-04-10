package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.clj.fastble.data.BleDevice;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattCharacter;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.model.BleGattService;
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
import com.petiyaak.box.util.LogUtils;
import com.petiyaak.box.util.NoFastClickUtils;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.IFingerView;
import java.util.UUID;
import butterknife.BindView;
import butterknife.OnClick;
import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;

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

    private FingerInfo fingerInfo;
    PetiyaakBoxInfo info;
    UserInfo userInfo;
    boolean isBind;
    private int postion = 0;

    BleDevice device;
    UUID readUuid;
    UUID writeUuid;
    UUID serverId;

    FingerDialog dialog;
    BluetoothClient mClient;

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
    public void initData() {
        fingerInfo = new FingerInfo();
        fingerInfo.leftFinger = true;
        mainTitleTitle.setText("Bind Finger");
        uesrItemName.setText(fingerInfo.userName);
        mainTitleBack.setVisibility(View.VISIBLE);

        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        userInfo = (UserInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_USER);
        isBind = getIntent().getBooleanExtra(ConstantEntiy.INTENT_BIND, false);
        initFinger();
        mClient = new BluetoothClient(this);
        if (!TextUtils.isEmpty(info.getBluetoothMac())) {
            BleConnectOptions options = new BleConnectOptions.Builder()
                    .setConnectRetry(3)   // 连接如果失败重试3次
                    .setConnectTimeout(30000)   // 连接超时30s
                    .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                    .setServiceDiscoverTimeout(20000)  // 发现服务超时20s
                    .build();
            mClient.connect(info.getBluetoothMac(), options, new BleConnectResponse() {
                @Override
                public void onResponse(int code, BleGattProfile profile) {
                    if (code == REQUEST_SUCCESS) {
                        initUUID(profile);
                    }
                }
            });
        }
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
        if (!TextUtils.isEmpty(info.getBluetoothMac())) {
            BleConnectOptions options = new BleConnectOptions.Builder()
                    .setConnectRetry(3)   // 连接如果失败重试3次
                    .setConnectTimeout(30000)   // 连接超时30s
                    .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                    .setServiceDiscoverTimeout(20000)  // 发现服务超时20s
                    .build();
            mClient.connect(info.getBluetoothMac(), options, new BleConnectResponse() {
                @Override
                public void onResponse(int code, BleGattProfile profile) {
                    if (code == REQUEST_SUCCESS) {
                        initUUID(profile);
                    }
                }
            });
        }

        findViewById(R.id.main_title_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("ATFDE=999".getBytes());
            }
        });
    }

    @Override
    protected FingerPresenter createPresenter() {
        return new FingerPresenter();
    }


    /**
     *  初始化相关id
     */
    private void initUUID(BleGattProfile profile) {
        if (profile != null && profile.getServices() != null) {
            for (BleGattService bchar : profile.getServices()) {
                if (bchar != null && bchar.getCharacters() != null && bchar.getUUID() != null) {
                    for(BleGattCharacter character :    bchar.getCharacters()) {
                        if (character != null && character.getUuid() != null) {
                            String uuid = character.getUuid().toString();
                            if (!TextUtils.isEmpty(uuid) && uuid.contains("fff4")) {
                                readUuid = character.getUuid();
                                LogUtils.e(TAG, "readUUid = " + readUuid);
                                serverId = bchar.getUUID();
                                LogUtils.e(TAG, "serverId = " + serverId);
                            }
                            if (!TextUtils.isEmpty(uuid) && uuid.contains("fff3")) {
                                writeUuid = character.getUuid();
                                LogUtils.e(TAG, "writeUuid = " + writeUuid);
                                serverId = bchar.getUUID();
                                LogUtils.e(TAG, "serverId = " + serverId);
                            }
                        }
                    }
                }
            }
        }
        if (serverId != null && readUuid != null) {
            mClient.notify(info.getBluetoothMac(), serverId, readUuid, new BleNotifyResponse() {
                @Override
                public void onNotify(UUID service, UUID character, byte[] value) {
                    LogUtils.e(TAG, "notify()  "+new String(value));
                    getRespone(new String(value).trim());
                }

                @Override
                public void onResponse(int code) {
                    LogUtils.e(TAG, "notify onResponse code =  "+code);
                    if (code == REQUEST_SUCCESS) {

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
        if (mClient == null || serverId == null || writeUuid == null) {
            LogUtils.e(TAG, "write() =  null");
            return;
        }
        mClient.write(info.getBluetoothMac(), serverId, writeUuid, value, new BleWriteResponse() {
            @Override
            public void onResponse(int code) {
                LogUtils.e(TAG, "write -- onResponse code= "+code);
            }
        });
    }

}
