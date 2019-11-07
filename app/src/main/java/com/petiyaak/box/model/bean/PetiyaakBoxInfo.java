package com.petiyaak.box.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by chenzhaolin on 2019/11/6.
 */
public class PetiyaakBoxInfo implements MultiItemEntity {

    private String itemTitle;
    private String itemName;
    private String itemPwd;
    private int type;

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPwd() {
        return itemPwd;
    }

    public void setItemPwd(String itemPwd) {
        this.itemPwd = itemPwd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PetiyaakBoxInfo(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
