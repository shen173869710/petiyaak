package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.jakewharton.rxbinding2.view.RxView;
import com.petiyaak.box.R;
import com.petiyaak.box.adapter.ShareListAdapter;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.event.BindSucessEvent;
import com.petiyaak.box.event.ConnectEvent;
import com.petiyaak.box.event.DelBoxEvent;
import com.petiyaak.box.event.ShareSucessEvent;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.PetiyaakInfoPresenter;
import com.petiyaak.box.util.ClientManager;
import com.petiyaak.box.util.ConnectResponse;
import com.petiyaak.box.util.LogUtils;
import com.petiyaak.box.util.NoFastClickUtils;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.IPetiyaakInfoView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class PetiyaakInfoActivity extends BaseActivity <PetiyaakInfoPresenter> implements IPetiyaakInfoView {


    private final String TAG = "PetiyaakInfoActivity";

    @BindView(R.id.main_title_back)
    RelativeLayout mainTitleBack;
    @BindView(R.id.main_title_title)
    TextView mainTitleTitle;
    @BindView(R.id.main_title_right)
    RelativeLayout mainTitleRight;
    @BindView(R.id.main_title_right_image)
    ImageView mainTitleRightImage;

    @BindView(R.id.info_icon)
    CircleImageView infoIcon;
    @BindView(R.id.petiyaak_info_name)
    TextView petiyaakInfoName;
    @BindView(R.id.petiyaak_info_status)
    TextView petiyaakInfoStatus;
    @BindView(R.id.info_blue)
    TextView infoBlue;
    @BindView(R.id.info_finger)
    TextView infoFinger;
    @BindView(R.id.info_option)
    TextView infoOption;
    @BindView(R.id.info_share)
    TextView infoShare;
    @BindView(R.id.petiyaak_info_battery)
    TextView petiyaakInfoBattery;

    @BindView(R.id.info_list)
    RecyclerView infoList;
    private PetiyaakBoxInfo info;
    List<UserInfo> userInfos = new ArrayList<>();
    private ShareListAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_petiyaak_info;
    }

    @Override
    public void initData() {
        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleTitle.setText("My petiyaakInfo Box");
        mainTitleRight.setVisibility(View.VISIBLE);
        mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);

        petiyaakInfoName.setText(info.getDeviceName());
        if (info.isItemBlueStatus()) {
            petiyaakInfoStatus.setText(R.string.connect);
            petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.dark));
        }else {
            petiyaakInfoStatus.setText(R.string.disconnect);
            petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.black));
        }
        infoList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShareListAdapter(userInfos, false);
        infoList.setAdapter(mAdapter);
        if (info.getDeviceId() > 0 ) {
            mPresenter.getUserListByDeviceId(info.getDeviceId(),true);
        }

        if (!TextUtils.isEmpty(info.getBluetoothMac())) {
            ClientManager.getInstance().registerConnectStatusListener(info.getBluetoothMac());
            ClientManager.getInstance().connectDevice(info.getBluetoothMac(), new ConnectResponse() {
                @Override
                public void onResponse(boolean isConnect) {
                    LogUtils.e("PetiyaakInfoActivity", "connect->onResponse" + isConnect);
                    if (isConnect) {
                        info.setItemBlueStatus(true);
                        startNotify();
                    } else {
                        info.setItemBlueStatus(false);
                    }
                    if (info.isItemBlueStatus()) {
                        petiyaakInfoStatus.setText(R.string.connect);
                        mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_con);
                        petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.dark));
                    } else {
                        petiyaakInfoStatus.setText(R.string.disconnect);
                        petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.black));
                        mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);
                    }
                }
            });
        }
    }


    public void startNotify() {
        ClientManager.getInstance().notifyData(info.getBluetoothMac(), new BleNotifyResponse() {

            @Override
            public void onResponse(int code) {
                LogUtils.e(TAG, "notify onResponse code =  " + code);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ClientManager.getInstance().writeData(info.getBluetoothMac(), ConstantEntiy.ATVOL.getBytes(), new BleWriteResponse() {
                            @Override
                            public void onResponse(int code) {
                                LogUtils.e(TAG, "writeData -- onResponse code= "+code);
                            }
                        });
                    }
                });

            }

            @Override
            public void onNotify(UUID service, UUID character, byte[] value) {
                LogUtils.e(TAG, "notify()  " + new String(value));
                getRespone(new String(value).trim());
            }
        });

    }
    /**
     *     获取指纹的返回值
     * @param respone
     */
    private void getRespone(String respone) {
        if (TextUtils.isEmpty(respone)) {
            return;
        }
        if (respone.contains(ConstantEntiy.ATVOL)) {
            String[] vol = respone.split("=");
            if (vol != null && vol.length == 2) {
                petiyaakInfoBattery.setText(vol[1] + "%");
            }
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

        RxView.clicks(infoBlue).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (!TextUtils.isEmpty(info.getBluetoothMac())) {
                    ToastUtils.showToast("Bluetooth device already bound");
                    return;
                }
                startActivity(BindPetiyaakActivity.getIntent(PetiyaakInfoActivity.this, info,true));
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
                            info.setItemBlueStatus(true);
                        } else {
                            info.setItemBlueStatus(false);
                        }
                        if (info.isItemBlueStatus()) {
                            petiyaakInfoStatus.setText(R.string.connect);
                            mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_con);
                            petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.dark));
                        } else {
                            petiyaakInfoStatus.setText(R.string.disconnect);
                            petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.black));
                            mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);
                        }
                    }
                });
            }
        });

        RxView.clicks(infoFinger).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (TextUtils.isEmpty(info.getBluetoothMac())) {
                    ToastUtils.showToast("No Bluetooth device");
                    return;
                }
                startActivity(FingerActivity.startIntent(PetiyaakInfoActivity.this,info,BaseApp.userInfo,true));
            }
        });

        RxView.clicks(infoShare).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (TextUtils.isEmpty(info.getBluetoothMac())) {
                    ToastUtils.showToast("No Bluetooth device");
                    return;
                }
                Intent intent = new Intent(PetiyaakInfoActivity.this, ShareActivity.class);
                intent.putExtra(ConstantEntiy.INTENT_BOX, info);
                startActivity(intent);
            }
        });

        RxView.clicks(infoOption).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (TextUtils.isEmpty(info.getBluetoothMac())) {
                    ToastUtils.showToast("No Bluetooth device");
                    return;
                }
                startActivity(OptionActivity.startIntent(PetiyaakInfoActivity.this,info,true ));
            }
        });

        mAdapter.addChildClickViewIds(R.id.share_submit);
        mAdapter.addChildClickViewIds(R.id.share_bind);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                UserInfo userInfo = userInfos.get(position);
                if ( view.getId() == R.id.share_submit ) {
                    startActivity( FingerDelActivity.startIntent(PetiyaakInfoActivity.this,info,userInfo,false));
                } else if (view.getId() == R.id.share_bind) {
                    startActivity(FingerActivity.startIntent(PetiyaakInfoActivity.this,info,userInfo,false));
                }
            }
        });
    }

    @Override
    protected PetiyaakInfoPresenter createPresenter() {
        return new PetiyaakInfoPresenter();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBindSuccess(BindSucessEvent event) {
        if (event != null) {
            info.setBluetoothName(event.getInfo().getBluetoothName());
            info.setItemBlueStatus(true);
            info.setBluetoothMac(event.getInfo().getBluetoothMac());
            info.setDeviceId(event.getInfo().getDeviceId());
            petiyaakInfoName.setText(info.getDeviceName());
            if (info.isItemBlueStatus()) {
                petiyaakInfoStatus.setText(R.string.connect);
                mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_con);
                petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.dark));
            }else {
                petiyaakInfoStatus.setText(R.string.disconnect);
                petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.black));
                mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectEvent(ConnectEvent event) {
        info.setItemBlueStatus(event.isConnect());
        if (info.isItemBlueStatus()) {
            petiyaakInfoStatus.setText(R.string.connect);
            mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_con);
            petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.dark));
        }else {
            petiyaakInfoStatus.setText(R.string.disconnect);
            petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.black));
            mainTitleRightImage.setBackgroundResource(R.mipmap.bluetooth_discon);;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareSucessEvent(ShareSucessEvent event) {
        mPresenter.getUserListByDeviceId(info.getDeviceId(),false);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDelBoxEvent(DelBoxEvent event) {
        LogUtils.e(TAG, "删除设备成功");
        finish();
    }



    @Override
    public void success(BaseRespone respone) {
        List<UserInfo> list = (List<UserInfo> )respone.getData();
        if (list != null) {
            userInfos.clear();
            userInfos.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void fail(Throwable error, Integer code, String msg) {
        ToastUtils.showToast(""+msg);
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
            ClientManager.getInstance().unRegisterConnectStatusListener(info.getBluetoothMac());
        }
    }
}
