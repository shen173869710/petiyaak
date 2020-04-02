package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.jakewharton.rxbinding2.view.RxView;
import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.customview.MClearEditText;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.SharePresenter;
import com.petiyaak.box.util.LogUtils;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.IShareView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class OptionActivity extends BaseActivity <SharePresenter> implements IShareView {
    @BindView(R.id.main_title_back)
    RelativeLayout mainTitleBack;
    @BindView(R.id.main_title_title)
    TextView mainTitleTitle;
    @BindView(R.id.main_title_right)
    RelativeLayout mainTitleRight;
    @BindView(R.id.setting_open)
    TextView settingOpen;
    @BindView(R.id.setting_send_value)
    MClearEditText settingSendValue;
    @BindView(R.id.setting_send)
    TextView settingSend;
    @BindView(R.id.setting_receive_value)
    MClearEditText settingReceiveValue;

    PetiyaakBoxInfo info;
    BleDevice device;

    String readUuid = "";
    String writeUuid = "";
    String serverId = "";


    public static Intent startIntent(Context content, PetiyaakBoxInfo box) {
        Intent intent = new Intent(content, FingerActivity.class);
        intent.putExtra(ConstantEntiy.INTENT_BOX, box);
        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_option;
    }


    @Override
    public void initData() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleTitle.setText("option box");
        RxView.clicks(mainTitleBack).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });

        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        mPresenter.getFingerprints(BaseApp.userInfo.getId(),info.getDeviceId());
    }

    @Override
    public void initListener() {
        List<BleDevice> devices = BleManager.getInstance().getAllConnectedDevice();
        if (devices == null || devices.size() == 0) {
            ToastUtils.showToast("no connect device");
            return;
        }

        int size = devices.size();
        for (int i = 0; i < size; i++) {
            if (info.getBluetoothName().equals(devices.get(i).getName())) {
                device = devices.get(i);
            }
        }

        if (device != null) {
            BluetoothGatt gatt = BleManager.getInstance().getBluetoothGatt(device);
            if (gatt != null) {
                for (BluetoothGattService service : gatt.getServices()) {
                    List<BluetoothGattCharacteristic> chars = service.getCharacteristics();
                    if (chars != null && chars.size() > 0) {
                        for (BluetoothGattCharacteristic bchar : chars) {
                            if (bchar != null) {
                                String uuid = bchar.getUuid().toString();
                                if (!TextUtils.isEmpty(uuid) && uuid.contains("fff4")) {
                                    readUuid = uuid;
                                    LogUtils.e("OptionActivity", "readUUid = "+readUuid);
                                    serverId = service.getUuid().toString();
                                    LogUtils.e("OptionActivity", "serverId = "+serverId);
                                }

                                if (!TextUtils.isEmpty(uuid) && uuid.contains("fff3")) {
                                    writeUuid = uuid;
                                    LogUtils.e("OptionActivity", "writeUuid = "+writeUuid);
                                    serverId = service.getUuid().toString();
                                    LogUtils.e("OptionActivity", "serverId = "+serverId);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(readUuid)) {
            BleManager.getInstance().notify(
                    device,
                    serverId,
                    readUuid,
                    new BleNotifyCallback() {

                        @Override
                        public void onNotifySuccess() {
                            ToastUtils.showToast("onNotifySuccess  ");
                        }

                        @Override
                        public void onNotifyFailure(final BleException exception) {
                            ToastUtils.showToast("onNotifyFailure  "+exception.toString());
                        }

                        @Override
                        public void onCharacteristicChanged(final byte[] data) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    settingReceiveValue.setText(new String(data)+"");
                                }
                            });
                        }
                    });

        }

        RxView.clicks(settingSend).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                String value = settingSendValue.getText().toString();
                if (!TextUtils.isEmpty(value)) {
                    write(value.getBytes());
                }
            }
        });


        RxView.clicks(settingOpen).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                write("ATLKO".getBytes());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoingEvent(int i) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void write(byte[] value) {
        BleManager.getInstance().write(
                device,
                serverId,
                writeUuid,
                value,
                new BleWriteCallback() {
                    @Override
                    public void onWriteSuccess(final int current, final int total, final byte[] justWrite) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast("onWriteSuccess  ");
                            }
                        });
                    }

                    @Override
                    public void onWriteFailure(final BleException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast("onWriteFailure  "+exception.toString());
                            }
                        });

                    }
                });
    }

    @Override
    public void success(BaseRespone respone) {
        PetiyaakBoxInfo boxInfo = (PetiyaakBoxInfo)respone.getData();
        if (boxInfo != null) {
            ToastUtils.showToast("success");
        }
    }

    @Override
    public void fail(Throwable error, Integer code, String msg) {

    }
}
