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
public class UserListAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {

    public UserListAdapter(@Nullable List<UserInfo> data) {
        super(R.layout.user_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, UserInfo item) {
        holder.setText(R.id.uesr_item_name, item.userName);
        showFinger(holder,item);
    }

    public void showFinger(BaseViewHolder holder,UserInfo item) {
        ImageView user_finger_1 = holder.getView(R.id.user_finger_1);
        TextView user_finger_1_value = holder.getView(R.id.user_finger_1_value);
        ImageView user_finger_2 = holder.getView(R.id.user_finger_2);
        TextView user_finger_2_value = holder.getView(R.id.user_finger_2_value);
        ImageView user_finger_3 = holder.getView(R.id.user_finger_3);
        TextView user_finger_3_value = holder.getView(R.id.user_finger_3_value);
        ImageView user_finger_4 = holder.getView(R.id.user_finger_4);
        TextView user_finger_4_value = holder.getView(R.id.user_finger_4_value);
        ImageView user_finger_5 = holder.getView(R.id.user_finger_5);
        TextView user_finger_5_value = holder.getView(R.id.user_finger_5_value);
        if (item.leftFinger) {
            holder.setTextColor(R.id.user_left_title, mContext.getResources().getColor(R.color.blue));
            holder.setBackgroundColor(R.id.user_left_icon, mContext.getResources().getColor(R.color.blue));
            holder.setTextColor(R.id.user_right_title, mContext.getResources().getColor(R.color.black_30));
            holder.setBackgroundColor(R.id.user_right_icon, mContext.getResources().getColor(R.color.black_30));
            showSel(user_finger_1, user_finger_1_value, item.finger1);
            showSel(user_finger_2, user_finger_2_value, item.finger2);
            showSel(user_finger_3, user_finger_3_value, item.finger3);
            showSel(user_finger_4, user_finger_4_value, item.finger4);
            showSel(user_finger_5, user_finger_5_value, item.finger5);
        }else {
            holder.setTextColor(R.id.user_left_title, mContext.getResources().getColor(R.color.black_30));
            holder.setBackgroundColor(R.id.user_left_icon, mContext.getResources().getColor(R.color.black_30));
            holder.setTextColor(R.id.user_right_title, mContext.getResources().getColor(R.color.blue));
            holder.setBackgroundColor(R.id.user_right_icon, mContext.getResources().getColor(R.color.blue));
            showSel(user_finger_1, user_finger_1_value, item.finger6);
            showSel(user_finger_2, user_finger_2_value, item.finger7);
            showSel(user_finger_3, user_finger_3_value, item.finger8);
            showSel(user_finger_4, user_finger_4_value, item.finger9);
            showSel(user_finger_5, user_finger_5_value, item.finger10);
        }


        holder.getView(R.id.user_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.leftFinger = true;
                notifyDataSetChanged();
            }
        });

        holder.getView(R.id.user_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.leftFinger = false;
                notifyDataSetChanged();
            }
        });
    }


    public void showSel (ImageView finger, TextView value, String fingerCode) {
        boolean isSel = false;
        if (!TextUtils.isEmpty(fingerCode)) {
            isSel = true;
        }
        if (isSel) {
            finger.setBackgroundResource(R.mipmap.finger_p);
            value.setTextColor(mContext.getResources().getColor(R.color.blue));
        }else {
            finger.setBackgroundResource(R.mipmap.finger_n);
            value.setTextColor(mContext.getResources().getColor(R.color.black_30));
        }
    }


}
