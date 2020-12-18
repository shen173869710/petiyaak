package com.petiyaak.box.adapter;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.petiyaak.box.R;
import com.petiyaak.box.util.ClientManager;
import com.petiyaak.box.util.LogUtils;

import java.util.List;


public class DeviceAdapter extends BaseQuickAdapter<BluetoothDevice, BaseViewHolder> {


    public DeviceAdapter(@Nullable List<BluetoothDevice> data) {
        super(R.layout.adapter_device, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BluetoothDevice item) {
        boolean isConnected = ClientManager.getInstance().getConnectStatus(item.getAddress());
        String name = item.getName();
        String mac = item.getAddress();
        LogUtils.e("Adapter", "name = "+ name + "    mac ="+mac);
        ImageView img_blue = helper.getView(R.id.img_blue);
        TextView txt_name = helper.getView(R.id.txt_name);
        TextView txt_mac = helper.getView(R.id.txt_mac);
        Button btn_connect = helper.getView(R.id.btn_connect);
        LinearLayout layout_connected = helper.getView(R.id.layout_connected);
        LinearLayout layout_idle = helper.getView(R.id.layout_idle);
        Button btn_disconnect = helper.getView(R.id.btn_disconnect);
        if (!TextUtils.isEmpty(name)) {
            txt_name.setText(name);
        }
        if (!TextUtils.isEmpty(mac)) {
            txt_mac.setText(mac);
        }

        if (isConnected) {
            img_blue.setImageResource(R.mipmap.ic_blue_connected);
            txt_name.setTextColor(0xFF1DE9B6);
            txt_mac.setTextColor(0xFF1DE9B6);
            layout_idle.setVisibility(View.GONE);
            layout_connected.setVisibility(View.VISIBLE);
        } else {
            img_blue.setImageResource(R.mipmap.ic_blue_remote);
            txt_name.setTextColor(0xFF000000);
            txt_mac.setTextColor(0xFF000000);
            layout_idle.setVisibility(View.VISIBLE);
            layout_connected.setVisibility(View.GONE);
        }

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onConnect(item);
                }
            }
        });

        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDisConnect(item);
                }
            }
        });
    }


    public void addDevice(BluetoothDevice bleDevice) {
        if(bleDevice == null){
            return;
        }
        if (removeDevice(bleDevice)) {
            return;
        }
        String name = bleDevice.getName();
        if(!TextUtils.isEmpty(name) && name.contains("YY")) {
            getData().add(0,bleDevice);
            notifyDataSetChanged();
        }

    }

    public boolean removeDevice(BluetoothDevice bleDevice) {
        int has = -1;
        for (int i = 0; i < getData().size(); i++) {
            BluetoothDevice device = getData().get(i);
            String name = device.getName();
            if (!TextUtils.isEmpty(name) && name.equals(bleDevice.getName())) {
                return true;
            }
        }
        return false;
    }


    public void clearScanDevice() {
        getData().clear();
        notifyDataSetChanged();
    }

    public interface OnDeviceClickListener {
        void onConnect(BluetoothDevice bleDevice);

        void onDisConnect(BluetoothDevice bleDevice);

        void onDetail(BluetoothDevice bleDevice);
    }

    private OnDeviceClickListener mListener;

    public void setOnDeviceClickListener(OnDeviceClickListener listener) {
        this.mListener = listener;
    }
}
