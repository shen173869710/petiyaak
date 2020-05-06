package com.petiyaak.box.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.jakewharton.rxbinding2.view.RxView;
import com.petiyaak.box.R;
import com.petiyaak.box.adapter.DeviceAdapter;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.event.BindSucessEvent;
import com.petiyaak.box.event.DialogDIsmissEvent;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.model.respone.BindDeviceRespone;
import com.petiyaak.box.presenter.BindPresenter;
import com.petiyaak.box.util.ClientManager;
import com.petiyaak.box.util.ConnectResponse;
import com.petiyaak.box.util.LogUtils;
import com.petiyaak.box.util.NoFastClickUtils;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.IBindView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;


public class BindPetiyaakActivity extends BaseActivity <BindPresenter> implements IBindView {

    private  final int REQUEST_CODE_OPEN_GPS = 1;
    private  final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    @BindView(R.id.main_title_back)
    RelativeLayout mainTitleBack;
    @BindView(R.id.main_title_title)
    TextView mainTitleTitle;
    @BindView(R.id.main_title_right)
    RelativeLayout mainTitleRight;
    @BindView(R.id.bluelist)
    RecyclerView bluelist;
    @BindView(R.id.bind_bluetooth)
    TextView bindBluetooth;

    private List<BluetoothDevice> data = new ArrayList<>();
    private DeviceAdapter mDeviceAdapter;
    private PetiyaakBoxInfo info;

    private String bluetoothName;
    private String bluetoothMac;
    private boolean isBind;


    public static Intent getIntent(Context context, PetiyaakBoxInfo info, boolean isBind) {
        Intent intent = new Intent(context, BindPetiyaakActivity.class);
        intent.putExtra(ConstantEntiy.INTENT_BOX, info);
        intent.putExtra(ConstantEntiy.INTENT_BIND, isBind);
        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_petiyaak;
    }


    @Override
    public void initData() {
        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleTitle.setText("Bind Petiyaak box");
        mainTitleRight.setVisibility(View.VISIBLE);

        bluelist.setLayoutManager(new LinearLayoutManager(this));
        mDeviceAdapter = new DeviceAdapter(data);

        mDeviceAdapter.setOnDeviceClickListener(new DeviceAdapter.OnDeviceClickListener() {
            @Override
            public void onConnect(BluetoothDevice bleDevice) {
                    connect(bleDevice);
            }

            @Override
            public void onDisConnect(BluetoothDevice bleDevice) {
                ClientManager.getInstance().disconnect(bleDevice.getAddress());
                mDeviceAdapter.clearScanDevice();

            }

            @Override
            public void onDetail(BluetoothDevice bleDevice) {
            }
        });
        bluelist.setAdapter(mDeviceAdapter);
    }

    @Override
    public void initListener() {
        RxView.clicks(mainTitleBack).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });


        RxView.clicks(mainTitleRight).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                checkPermissions();
            }
        });

        bindBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }

                if(TextUtils.isEmpty(bluetoothMac) || TextUtils.isEmpty(bluetoothName)){
                    ToastUtils.showToast("Please connect buletooth");
                    return;
                }
                mPresenter.bindDeviced(info.getDeviceName(), bluetoothName, bluetoothMac, BaseApp.userInfo.getId());
            }
        });
    }

    @Override
    protected BindPresenter createPresenter() {
        return new BindPresenter();
    }

    @Override
    public Activity getActivity() {
        return this;
    }



    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            ToastUtils.showToast("Please turn on Bluetooth ");
            return;
        }

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(this, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }


    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.notifyTitle)
                            .setMessage(R.string.gpsNotifyMsg)
                            .setNegativeButton(R.string.cancel,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                            .setPositiveButton(R.string.setting,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                                        }
                                    })

                            .setCancelable(false)
                            .show();
                } else {
                    setScanRule();
                    startScan();
                }
                break;
        }
    }

    private void startScan() {
        showDialog();
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
        ClientManager.getInstance().getClient().search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {

            }

            @Override
            public void onDeviceFounded(SearchResult result) {
//                Beacon beacon = new Beacon(device.scanRecord);
//                BluetoothLog.v(String.format("beacon for %s\n%s", device.getAddress(), beacon.toString()));
                mDeviceAdapter.addDevice(result.device);
            }

            @Override
            public void onSearchStopped() {
                dismissDialog();
            }

            @Override
            public void onSearchCanceled() {
                dismissDialog();
            }
        });
    }


    private void setScanRule() {
        boolean isAutoConnect = false;
//        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
//                .setAutoConnect(isAutoConnect)      // 连接时的autoConnect参数，可选，默认false
//                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
//                .build();
//        BleManager.getInstance().initScanRule(scanRuleConfig);
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    private void connect(final BluetoothDevice bleDevice) {
        showDialog();
        ClientManager.getInstance().connectDevice(bleDevice.getAddress(), new ConnectResponse() {
            @Override
            public void onResponse(boolean isConnect) {
                LogUtils.e("bind",  "isConnect = " + isConnect);
                if (isConnect) {
                    bluetoothName = bleDevice.getName();
                    bluetoothMac = bleDevice.getAddress();
                    mDeviceAdapter.addDevice(bleDevice);
                    mDeviceAdapter.notifyDataSetChanged();
                    bluelist.scrollToPosition(0);

                }else {
                    Toast.makeText(BindPetiyaakActivity.this, getString(R.string.connect_fail), Toast.LENGTH_LONG).show();
                }
                dismissDialog();
            }
        });


//        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
//            @Override
//            public void onStartConnect() {
//                showDialog();
//            }
//            @Override
//            public void onConnectFail(BleDevice bleDevice, BleException exception) {
//                dismissDialog();
//                Toast.makeText(BindPetiyaakActivity.this, getString(R.string.connect_fail)+exception.getDescription(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
//                dismissDialog();
//                bluetoothName = bleDevice.getName();
//                bluetoothMac = bleDevice.getMac();
//                mDeviceAdapter.addDevice(bleDevice);
//                mDeviceAdapter.notifyDataSetChanged();
//                bluelist.scrollToPosition(0);
//            }
//
//            @Override
//            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
//                dismissDialog();
//                mDeviceAdapter.removeDevice(bleDevice);
//                mDeviceAdapter.notifyDataSetChanged();
//                if (isActiveDisConnected) {
//                    Toast.makeText(BindPetiyaakActivity.this, getString(R.string.active_disconnected), Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(BindPetiyaakActivity.this, getString(R.string.disconnected), Toast.LENGTH_LONG).show();
//                    /**
//                     *   链接成功
//                     */
//                }
//
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_GPS) {
            if (checkGPSIsOpen()) {
                setScanRule();
                startScan();
            }
        }
    }

    @Override
    public void bindSuccess(BaseRespone respone) {
        BindDeviceRespone bRespone = (BindDeviceRespone)respone.data;
        if (bRespone != null) {
            if (!TextUtils.isEmpty(info.getDeviceName()) && info.getDeviceName().equals(bRespone.getDeviceName())) {
                info.setBluetoothName(bRespone.getBluetoothName());
                info.setItemBlueStatus(true);
                info.setBluetoothMac(bRespone.getBluetoothMac());
                info.setDeviceName(bRespone.getDeviceName());
                info.setDeviceId(bRespone.getDeviceId());
                EventBus.getDefault().post(new BindSucessEvent(info));
                ToastUtils.showToast("device bound successful");
                finish();
            }else {
                ToastUtils.showToast("The device has been bound");
            }
        }else {
            ToastUtils.showToast("The device has been bound");
        }

    }

    @Override
    public void bindFail(Throwable error, Integer code, String msg) {
        ToastUtils.showToast(msg+"");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDialogDIsmissEvent(DialogDIsmissEvent  event) {
        ClientManager.getInstance().getClient().stopSearch();
    }
}
