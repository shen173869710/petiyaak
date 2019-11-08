package com.petiyaak.box.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by chenzhaolin on 2019/11/6.
 */
public class PetiyaakBoxInfo implements MultiItemEntity {

    private String itemUserName;
    private String itemBlueName;
    private String itemUserPwd;
    private boolean itemBlueStatus;
    private int type;


    public PetiyaakBoxInfo(int type) {
        this.type = type;
    }

    public String getItemUserName() {
        return itemUserName;
    }

    public void setItemUserName(String itemUserName) {
        this.itemUserName = itemUserName;
    }

    public String getItemBlueName() {
        return itemBlueName;
    }

    public void setItemBlueName(String itemBlueName) {
        this.itemBlueName = itemBlueName;
    }

    public String getItemUserPwd() {
        return itemUserPwd;
    }

    public void setItemUserPwd(String itemUserPwd) {
        this.itemUserPwd = itemUserPwd;
    }

    public boolean isItemBlueStatus() {
        return itemBlueStatus;
    }

    public void setItemBlueStatus(boolean itemBlueStatus) {
        this.itemBlueStatus = itemBlueStatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
