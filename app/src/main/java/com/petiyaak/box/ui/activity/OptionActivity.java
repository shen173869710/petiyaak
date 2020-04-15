package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.jakewharton.rxbinding2.view.RxView;
import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.customview.MClearEditText;
import com.petiyaak.box.event.ConnectEvent;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.CommonPresenter;
import com.petiyaak.box.util.ClientManager;
import com.petiyaak.box.util.ConnectResponse;
import com.petiyaak.box.util.LogUtils;
import com.petiyaak.box.util.NoFastClickUtils;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.ICommonView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class OptionActivity extends BaseActivity <CommonPresenter> implements ICommonView {
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
    @BindView(R.id.main_title_right_image)
    ImageView mainTitleRightImage;

    @BindView(R.id.setting_del)
    TextView settingDel;


    private final String TAG = "OptionActivit";


    public static Intent startIntent(Context content, PetiyaakBoxInfo box) {
        Intent intent = new Intent(content, OptionActivity.class);
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
        mainTitleRight.setVisibility(View.VISIBLE);
        mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);
        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        //mPresenter.getFingerprints(BaseApp.userInfo.getId(),info.getDeviceId());



        /**
         *   链接设备
         */
        if (!TextUtils.isEmpty(info.getBluetoothMac())) {
            showWaitingDialog(getResources().getString(R.string.connect_loading)).show();
            ClientManager.getInstance().connectDevice(info.getBluetoothMac(), new ConnectResponse() {
                @Override
                public void onResponse(boolean isConnect) {
                    dismissDialog();
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
        if (respone.contains(ConstantEntiy.ATLKO_OK)) {
            ToastUtils.showToast("open box successful， code "+respone);
        }else {
            ToastUtils.showToast("open box error， error code "+respone);
        }


        if(respone.contains(ConstantEntiy.ATFDE_OK)) {
            ToastUtils.showToast("delete all finger successful");
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

        RxView.clicks(settingSend).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                String value = settingSendValue.getText().toString();
                if (!TextUtils.isEmpty(value)) {
                    write(value.getBytes());
                }
            }
        });


        RxView.clicks(settingOpen).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                write(ConstantEntiy.ATLKO.getBytes());
            }
        });



        RxView.clicks(settingDel).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                write(ConstantEntiy.getATFDEstirng(999).getBytes());
            }
        });


        findViewById(R.id.main_title_right_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                showWaitingDialog(getResources().getString(R.string.connect_loading)).show();
                ClientManager.getInstance().connectDevice(info.getBluetoothMac(), new ConnectResponse() {
                    @Override
                    public void onResponse(boolean isConnect) {
                        dismissDialog();
                        if (isConnect) {
                            mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_con);
                        } else {
                            mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);
                        }
                    }
                });
            }
        });

    }

    @Override
    protected CommonPresenter createPresenter() {
        return new CommonPresenter();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void success(BaseRespone respone) {
        PetiyaakBoxInfo boxInfo = (PetiyaakBoxInfo)respone.getData();

    }

    @Override
    public void fail(Throwable error, Integer code, String msg) {

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
