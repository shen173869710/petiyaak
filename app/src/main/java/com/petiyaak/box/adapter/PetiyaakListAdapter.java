package com.petiyaak.box.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
                helper.setText(R.id.petiyaak_item_name,item.getItemName());
                break;

        }
    }
}
