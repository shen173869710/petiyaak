package com.petiyaak.box.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.model.bean.SettingItem;

import java.util.List;


public class SettingListAdapter extends BaseMultiItemQuickAdapter<SettingItem, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SettingListAdapter(List data) {
        super(data);
        addItemType(0, R.layout.setting_tab_item_0);
        addItemType(1, R.layout.setting_tab_item_1);
        addItemType(2, R.layout.setting_tab_item_2);
        addItemType(3, R.layout.setting_tab_item_3);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SettingItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                int id = 0;
                if (BaseApp.userInfo.getAvatarid() > 10) {
                    id = 0;
                }
                id = BaseApp.userInfo.getAvatarid();
                helper.setImageResource(R.id.setting_icon, ConstantEntiy.USER_HEAD_ID[id]);
                helper.setText(R.id.setting_username,BaseApp.userInfo.getUsername());
                helper.setText(R.id.setting_phone,BaseApp.userInfo.getPhonenumber()+"");
                break;
            case 1:
                helper.setText(R.id.setting_item_title, item.getItemTitle());
                break;
            case 2:
                helper.setText(R.id.setting_item_title, item.getItemTitle());
                break;
            case 3:
                helper.setText(R.id.setting_item_title, item.getItemTitle());
                break;
        }
    }
}
