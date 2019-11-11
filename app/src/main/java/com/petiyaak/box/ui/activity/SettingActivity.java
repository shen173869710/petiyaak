package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
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
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.customview.MClearEditText;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.presenter.BasePresenter;
import com.petiyaak.box.util.LogUtils;
import com.petiyaak.box.util.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class SettingActivity extends BaseActivity {
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
    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }


    @Override
    public void initData() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleTitle.setText("setting");
        RxView.clicks(mainTitleBack).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });

        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
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
            if (info.getItemBlueName().equals(devices.get(i).getName())) {
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
                                    LogUtils.e("SettingActivity", "readUUid = "+readUuid);
                                    serverId = service.getUuid().toString();
                                    LogUtils.e("SettingActivity", "serverId = "+serverId);
                                }

                                if (!TextUtils.isEmpty(uuid) && uuid.contains("fff3")) {
                                    writeUuid = uuid;
                                    LogUtils.e("SettingActivity", "writeUuid = "+writeUuid);
                                    serverId = service.getUuid().toString();
                                    LogUtils.e("SettingActivity", "serverId = "+serverId);
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
}
