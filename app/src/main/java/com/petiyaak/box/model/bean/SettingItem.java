package com.petiyaak.box.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by chenzhaolin on 2019/11/5.
 */
public class SettingItem implements MultiItemEntity {
    private String itemTitle;
    private int type;
    @Override
    public int getItemType() {
        return type;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
