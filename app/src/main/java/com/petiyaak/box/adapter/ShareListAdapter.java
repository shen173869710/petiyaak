package com.petiyaak.box.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.petiyaak.box.R;
import com.petiyaak.box.model.bean.ModelEntiy;
import com.petiyaak.box.model.bean.UserInfo;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class ShareListAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {
    private boolean isShare;
    public ShareListAdapter(@Nullable List<UserInfo> data, boolean share) {
        super(R.layout.share_list_item, data);
        this.isShare = share;
    }

    @Override
    protected void convert(BaseViewHolder holder, UserInfo item) {
        holder.setImageResource(R.id.share_icon, ModelEntiy.USER_HEAD_ID[item.getAvatarid()]);
        holder.setText(R.id.share_account, item.getUsername()+"");
        holder.setText(R.id.share_name, item.getPhonenumber()+"");

        if (isShare) {
            holder.setText(R.id.share_submit, "share");
        }else {
            holder.setText(R.id.share_submit, "cancle share");
        }
    }
}
