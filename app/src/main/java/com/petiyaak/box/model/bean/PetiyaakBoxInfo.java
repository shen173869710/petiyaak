package com.petiyaak.box.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 *  设备表
 */
public class PetiyaakBoxInfo implements MultiItemEntity, Serializable {
    private int deviceId;
    private String deviceName;
    private String bluetoothName;
    private String bluetoothMac;
    private String bluetoothPwd;
    private boolean itemBlueStatus;
    private int isOwner;
    private int userId;
    private long utime;

    private String imageUrl;
    private int imageUrlId;

    private int leftIndex;
    private int leftThumb;
    private int leftMiddle;
    private int leftRing;
    private int leftLittle;

    private int rightIndex;
    private int rightThumb;
    private int rightMiddle;
    private int rightRing;
    private int rightLittle;
    private int type = 1;


    public PetiyaakBoxInfo(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }


    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public String getBluetoothPwd() {
        return bluetoothPwd;
    }

    public void setBluetoothPwd(String bluetoothPwd) {
        this.bluetoothPwd = bluetoothPwd;
    }

    public boolean isItemBlueStatus() {
        return itemBlueStatus;
    }

    public void setItemBlueStatus(boolean itemBlueStatus) {
        this.itemBlueStatus = itemBlueStatus;
    }

    public int getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(int isOwner) {
        this.isOwner = isOwner;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
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

    public int getLeftIndex() {
        return leftIndex;
    }

    public void setLeftIndex(int leftIndex) {
        this.leftIndex = leftIndex;
    }

    public int getLeftThumb() {
        return leftThumb;
    }

    public void setLeftThumb(int leftThumb) {
        this.leftThumb = leftThumb;
    }

    public int getLeftMiddle() {
        return leftMiddle;
    }

    public void setLeftMiddle(int leftMiddle) {
        this.leftMiddle = leftMiddle;
    }

    public int getLeftRing() {
        return leftRing;
    }

    public void setLeftRing(int leftRing) {
        this.leftRing = leftRing;
    }

    public int getLeftLittle() {
        return leftLittle;
    }

    public void setLeftLittle(int leftLittle) {
        this.leftLittle = leftLittle;
    }

    public int getRightIndex() {
        return rightIndex;
    }

    public void setRightIndex(int rightIndex) {
        this.rightIndex = rightIndex;
    }

    public int getRightThumb() {
        return rightThumb;
    }

    public void setRightThumb(int rightThumb) {
        this.rightThumb = rightThumb;
    }

    public int getRightMiddle() {
        return rightMiddle;
    }

    public void setRightMiddle(int rightMiddle) {
        this.rightMiddle = rightMiddle;
    }

    public int getRightRing() {
        return rightRing;
    }

    public void setRightRing(int rightRing) {
        this.rightRing = rightRing;
    }

    public int getRightLittle() {
        return rightLittle;
    }

    public void setRightLittle(int rightLittle) {
        this.rightLittle = rightLittle;
    }
}
