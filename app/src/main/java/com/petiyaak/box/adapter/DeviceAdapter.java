package com.petiyaak.box.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.petiyaak.box.R;

import java.util.List;


public class DeviceAdapter extends BaseQuickAdapter<BleDevice, BaseViewHolder> {


    public DeviceAdapter(@Nullable List<BleDevice> data) {
        super(R.layout.adapter_device, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BleDevice item) {
        boolean isConnected = BleManager.getInstance().isConnected(item);
        String name = item.getName();
        String mac = item.getMac();
        int rssi = item.getRssi();
        ImageView img_blue = helper.getView(R.id.img_blue);
        TextView txt_name = helper.getView(R.id.txt_name);
        TextView txt_mac = helper.getView(R.id.txt_mac);
        Button btn_connect = helper.getView(R.id.btn_connect);
        LinearLayout layout_connected = helper.getView(R.id.layout_connected);
        LinearLayout layout_idle = helper.getView(R.id.layout_idle);
        Button btn_disconnect = helper.getView(R.id.btn_disconnect);

        txt_name.setText(name);
        txt_mac.setText(mac);
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


    public void addDevice(BleDevice bleDevice) {
        removeDevice(bleDevice);
        mData.add(0,bleDevice);
    }

    public void removeDevice(BleDevice bleDevice) {
        int has = -1;
        for (int i = 0; i < mData.size(); i++) {
            BleDevice device = mData.get(i);
            if (bleDevice.getKey().equals(device.getKey())) {
               has = i;
            }
        }
        if (has >= 0) {
            mData.remove(has);
        }
    }


    public void clearScanDevice() {
        mData.clear();
    }

    public interface OnDeviceClickListener {
        void onConnect(BleDevice bleDevice);

        void onDisConnect(BleDevice bleDevice);

        void onDetail(BleDevice bleDevice);
    }

    private OnDeviceClickListener mListener;

    public void setOnDeviceClickListener(OnDeviceClickListener listener) {
        this.mListener = listener;
    }
}
