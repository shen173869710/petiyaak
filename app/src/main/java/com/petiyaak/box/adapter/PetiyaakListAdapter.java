package com.petiyaak.box.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.petiyaak.box.R;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;

import java.util.List;


public class PetiyaakListAdapter extends BaseMultiItemQuickAdapter<PetiyaakBoxInfo, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PetiyaakListAdapter(List data) {
        super(data);
        addItemType(0, R.layout.petiyaak_item_0);
        addItemType(1, R.layout.petiyaak_item_1);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PetiyaakBoxInfo item) {
        switch (helper.getItemViewType()) {
            case 0:
                break;
            case 1:
                helper.setText(R.id.petiyaak_item_name,item.getItemUserName());
                if (item.isItemBlueStatus()) {
                    helper.getView(R.id.petiyaak_blue_layout).setVisibility(View.VISIBLE);
                    helper.setText(R.id.petiyaak_blue_name, item.getItemBlueName());
                    helper.getView(R.id.petiyaak_blue_status).setBackgroundResource(R.mipmap.ic_blue_remote);
                }else {
                    helper.getView(R.id.petiyaak_blue_layout).setVisibility(View.INVISIBLE);
                }
                break;

        }
    }
}
