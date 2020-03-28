package com.petiyaak.box.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.petiyaak.box.R;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import java.util.List;
import io.reactivex.annotations.Nullable;

public class ShareDeviceListAdapter extends BaseQuickAdapter<PetiyaakBoxInfo, BaseViewHolder> {
    public ShareDeviceListAdapter(@Nullable List<PetiyaakBoxInfo> data) {
        super(R.layout.share_device_list_item, data);

    }

    @Override
    protected void convert(BaseViewHolder holder, PetiyaakBoxInfo item) {
        holder.setText(R.id.share_account, item.getDeviceName()+"");
        holder.setText(R.id.share_name, item.getBluetoothName()+"");

    }
}
