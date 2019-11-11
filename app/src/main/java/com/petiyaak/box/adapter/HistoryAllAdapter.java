package com.petiyaak.box.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.petiyaak.box.R;
import com.petiyaak.box.model.bean.UserInfo;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**

 */
public class HistoryAllAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {

    public HistoryAllAdapter(@Nullable List<UserInfo> data) {
        super(R.layout.history_all_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, UserInfo item) {
    }

}
