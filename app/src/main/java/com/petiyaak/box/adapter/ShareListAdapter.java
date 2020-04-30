package com.petiyaak.box.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.constant.ConstantEntiy;
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
        int id = 0;
        if (BaseApp.userInfo.getAvatarid() > 10) {
            id = 0;
        }
        id = BaseApp.userInfo.getAvatarid();
        holder.setImageResource(R.id.share_icon, ConstantEntiy.USER_HEAD_ID[id]);
        holder.setText(R.id.share_account, item.getUsername()+"");
        holder.setText(R.id.share_name, item.getPhonenumber()+"");

        TextView share_bind = holder.getView(R.id.share_bind);
        if (isShare) {
            holder.setText(R.id.share_submit, "share");
            share_bind.setVisibility(View.GONE);
        }else {
            holder.setText(R.id.share_submit, "cancle");
            share_bind.setVisibility(View.VISIBLE);
        }
    }
}
