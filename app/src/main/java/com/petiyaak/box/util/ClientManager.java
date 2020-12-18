package com.petiyaak.box.util;

import android.text.TextUtils;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattCharacter;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.model.BleGattService;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.event.ConnectEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.UUID;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

public class ClientManager {

    private static final String TAG = "ClientManager";
    private static ClientManager mClientManager;
    private static BluetoothClient mClient;

    private UUID mReadUuid;
    private UUID mWriteUuid;
    private UUID mServerId;

    private BluetoothStateListener mBluetoothStateListener;
    private BleConnectStatusListener mBleConnectStatusListener;

    public ClientManager() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(BaseApp.getInstance());
                }
            }
        }
    }

    public static ClientManager getInstance(){
        if (mClientManager == null) {
            mClientManager = new ClientManager();
        }
        return mClientManager;
    }
    public  BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(BaseApp.getInstance());
                }
            }
        }
        return mClient;
    }

    /**
     *   链接设备
     */
    public void connectDevice(String mac, ConnectResponse response) {
        if (TextUtils.isEmpty(mac)) {
            LogUtils.e(TAG, "蓝牙地址为空   74");
            return;
        }
        if (!mClient.isBluetoothOpened()) {
            ToastUtils.showToast("The bluetooth of the mobile phone is not turned on, please turn on the bluetooth of the mobile phone");
        }
        getClient().clearRequest(mac, 0);
        getClient().refreshCache(mac);
        if (getConnectStatus(mac) && mServerId != null && mWriteUuid != null && mReadUuid != null) {
            response.onResponse(true);
            LogUtils.e(TAG, "设备已经链接");
        }else {
            LogUtils.e(TAG, "设备未连接 开始链接");
            BleConnectOptions options = new BleConnectOptions.Builder()
                    .setConnectRetry(3)
                    .setConnectTimeout(10000)
                    .setServiceDiscoverRetry(3)
                    .setServiceDiscoverTimeout(10000)
                    .build();
            getClient().connect(mac, options, new BleConnectResponse() {
                @Override
                public void onResponse(int code, BleGattProfile profile) {
                    if (code == REQUEST_SUCCESS) {
                        initUUID(profile);
                        response.onResponse(true);
                    }else {
                        response.onResponse(false);
                    }

                }
            });
        }
    }
    /**
     *        获取设备的链接状态
     * @param mac
     * @return
     */
    public boolean getConnectStatus(String mac) {
        if (TextUtils.isEmpty(mac)) {
            LogUtils.e(TAG, "蓝牙地址为空   113");
            return false;
        }


        int status = mClient.getConnectStatus(mac);
        if (status == Constants.STATUS_DEVICE_CONNECTED) {
            return true;
        }
        return false;
    }


    /**
     *    注册蓝牙链接状态时间
     */
    public void registerBluetoothStateListener() {
        if (mBluetoothStateListener == null) {
            mBluetoothStateListener = new BluetoothStateListener() {
                @Override
                public void onBluetoothStateChanged(boolean openOrClosed) {
                    if (openOrClosed) {
                        ToastUtils.showToast("The phone's bluetooth is on");
                    }else {
                        ToastUtils.showToast("The bluetooth of the mobile phone is not turned on, please turn on the bluetooth of the mobile phone");
                    }
                }
            };
        }
        getClient().registerBluetoothStateListener(mBluetoothStateListener);
    }

    public void unRegisterBluetoothStateListener() {
        if (mBluetoothStateListener != null) {
            getClient().unregisterBluetoothStateListener(mBluetoothStateListener);
        }
    }

    public void registerConnectStatusListener(String mac) {
        if (mBleConnectStatusListener == null) {
            mBleConnectStatusListener = new BleConnectStatusListener() {
                @Override
                public void onConnectStatusChanged(String mac, int status) {
                    if (status == STATUS_CONNECTED) {
                        EventBus.getDefault().post(new ConnectEvent(true));
                    } else if (status == STATUS_DISCONNECTED) {
                        EventBus.getDefault().post(new ConnectEvent(false));
                    }
                }
            };
        }
        getClient().registerConnectStatusListener(mac, mBleConnectStatusListener);
    }

    public void unRegisterConnectStatusListener(String mac) {
        getClient().unregisterConnectStatusListener(mac, mBleConnectStatusListener);
    }


    /**
     *  初始化相关id
     */
    private void initUUID(BleGattProfile profile) {
        LogUtils.e(TAG, "设备链接成功 初始化uuid");
        if (profile != null && profile.getServices() != null) {
            for (BleGattService bchar : profile.getServices()) {
                if (bchar != null && bchar.getCharacters() != null && bchar.getUUID() != null) {
                    for (BleGattCharacter character : bchar.getCharacters()) {
                        if (character != null && character.getUuid() != null) {
                            LogUtils.e(TAG, "uuid = " + character.getUuid());
                            String uuid = character.getUuid().toString();
                            if (!TextUtils.isEmpty(uuid) && uuid.contains("FF26")) {
                                mReadUuid = character.getUuid();
                                LogUtils.e(TAG, "readUUid = " + mReadUuid);
                                mServerId = bchar.getUUID();
                                LogUtils.e(TAG, "serverId = " + mServerId);
                            }
                            if (!TextUtils.isEmpty(uuid) && uuid.contains("ff69")) {
                                mWriteUuid = character.getUuid();
                                LogUtils.e(TAG, "writeUuid = " + mWriteUuid);
                                mServerId = bchar.getUUID();
                                LogUtils.e(TAG, "serverId = " + mServerId);
                            }
                        }
                    }
                }
            }
        }
    }

    public void writeData(String mac, byte[] value, BleWriteResponse response) {
        LogUtils.e(TAG, "写入数据 ====》"+new String(value));
        if (getUUid(mac)) {
            getClient().write(mac, mServerId, mWriteUuid, value, response);
        }

    }

    public void notifyData(String mac, BleNotifyResponse response) {
        LogUtils.e(TAG, "mServerId = "+mServerId+ "----mReadUuid = "+mReadUuid);
        if (getUUid(mac)) {
            getClient().notify(mac, mServerId, mReadUuid, response);
        }
    }

    public void unnotifyData(String mac, BleUnnotifyResponse response) {
        if (getUUid(mac)) {
            getClient().unnotify(mac, mServerId, mReadUuid, response);
        }
    }


    public boolean getUUid (String mac) {
        if (mServerId == null || mWriteUuid == null) {
            BleConnectOptions options = new BleConnectOptions.Builder()
                    .setConnectRetry(3)
                    .setConnectTimeout(10000)
                    .setServiceDiscoverRetry(3)
                    .setServiceDiscoverTimeout(10000)
                    .build();
            getClient().connect(mac, options, new BleConnectResponse() {
                @Override
                public void onResponse(int code, BleGattProfile profile) {
                    if (code == REQUEST_SUCCESS) {
                        initUUID(profile);
                    }else {
                    }
                }
            });
            return false;
        }
        return true;
    }

    public void disconnect(String mac) {
        LogUtils.e(TAG, "========disconnect============");
        mReadUuid = null;
        mServerId = null;
        mWriteUuid = null;
        getClient().disconnect(mac);
    }

    public void onDestroy(){
        ClientManager.getInstance().unRegisterBluetoothStateListener();
    }

}
