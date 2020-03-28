package com.petiyaak.box.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.petiyaak.box.R;
import com.petiyaak.box.adapter.SettingListAdapter;
import com.petiyaak.box.base.BaseFragment;
import com.petiyaak.box.model.bean.SettingItem;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.model.respone.VersionRespone;
import com.petiyaak.box.presenter.SharePresenter;
import com.petiyaak.box.view.IShareView;

import java.util.ArrayList;

import butterknife.BindView;
/**
 * Created by chenzhaolin on 2019/7/10.
 */

public class SettingFragment extends BaseFragment <SharePresenter> implements IShareView {

    @BindView(R.id.setting_list)
    RecyclerView settingList;
    private SettingListAdapter mAdapter;
    private View view;

    private String shareUrl = "hello";
    private ArrayList<SettingItem>settingItems = new ArrayList<>();

    public int[]items = {
        R.string.setting_item_1,
            R.string.setting_item_2,
            R.string.setting_item_3,
            R.string.setting_item_4,
            R.string.setting_item_5,
            R.string.setting_item_6,
            R.string.setting_item_7,
            R.string.setting_item_8,
            R.string.setting_item_9,
            R.string.setting_item_10,
            R.string.setting_item_11
    };

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }


    @Override
    protected SharePresenter createPresenter() {
        return new SharePresenter();
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_setting, null);
    }

    @Override
    protected void initView() {
        view = mView.findViewById(R.id.main_title_bar);
        TextView title = view.findViewById(R.id.main_title_title);
        title.setText(R.string.main_tab_4);
        settingList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SettingListAdapter(settingItems);
        settingList.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if(position == 8) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    // 比如发送文本形式的数据内容
                    sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "share to"));
                }
            }
        });

        mPresenter.checkVersion(0);
    }

    @Override
    protected void initData() {
        int length = items.length;
        for (int i = 0; i < length; i++) {
            SettingItem item = new SettingItem();
            item.setItemTitle(getString(items[i]));
            if (i == 0) {
                item.setType(0);
            }else if (i == 1 || i == 3 || i ==6) {
                item.setType(1);
            }else if (i == 4 || i == 5) {
                item.setType(3);
            }else {
                item.setType(2);
            }
            settingItems.add(item);
        }

    }

    @Override
    public void initListener() {

    }


    @Override
    public void firstLoad() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        rootView.setFocusableInTouchMode(true);
        return rootView;
    }

    @Override
    public void success(BaseRespone respone) {
        VersionRespone versionRespone = (VersionRespone)respone.getData();
        if (versionRespone != null) {
            shareUrl = versionRespone.getFileUrl();
        }
    }

    @Override
    public void fail(Throwable error, Integer code, String msg) {

    }
}
