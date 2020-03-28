package com.petiyaak.box.model.respone;

public class BindDeviceRespone {

    private String bluetoothMac;
    private String bluetoothName;
    private long deviceBindTime;
    private int deviceId;
    private int deviceOwnerId;
    private String deviceName;
    private String imageUrl;
    private int imageUrlId;
    private int status;

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public long getDeviceBindTime() {
        return deviceBindTime;
    }

    public void setDeviceBindTime(long deviceBindTime) {
        this.deviceBindTime = deviceBindTime;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeviceOwnerId() {
        return deviceOwnerId;
    }

    public void setDeviceOwnerId(int deviceOwnerId) {
        this.deviceOwnerId = deviceOwnerId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageUrlId() {
        return imageUrlId;
    }

    public void setImageUrlId(int imageUrlId) {
        this.imageUrlId = imageUrlId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
