package com.petiyaak.box.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.petiyaak.box.R;
import com.petiyaak.box.adapter.ShareDeviceListAdapter;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.base.BaseFragment;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.ShareListPresenter;
import com.petiyaak.box.ui.activity.FingerActivity;
import com.petiyaak.box.ui.activity.OptionActivity;
import com.petiyaak.box.view.IShareListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenzhaolin on 2019/7/10.
 */

public class SharedFragment extends BaseFragment <ShareListPresenter> implements IShareListView {
    private View view;
    @BindView(R.id.share_list)
    RecyclerView shareList;
    ShareDeviceListAdapter mAdapter;
    List<PetiyaakBoxInfo> infos = new ArrayList<>();

    public static SharedFragment newInstance( ){
        SharedFragment fragment = new SharedFragment();
        return fragment;
    }

    @Override
    protected ShareListPresenter createPresenter() {
        return new ShareListPresenter();
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_shared, null);
    }

    @Override
    protected void initView() {
        view = mView.findViewById(R.id.main_title_bar);
        TextView title = view.findViewById(R.id.main_title_title);
        title.setText(R.string.main_tab_2);
        shareList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ShareDeviceListAdapter(infos);
        shareList.setAdapter(mAdapter);
        mAdapter.addChildClickViewIds(R.id.share_submit);
        mAdapter.addChildClickViewIds(R.id.share_bind);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.share_submit) {
                    startActivity(OptionActivity.startIntent(mContext,infos.get(position)));
                }else if (view.getId() == R.id.share_bind) {
                    startActivity(FingerActivity.startIntent(mContext,infos.get(position), BaseApp.userInfo,false));
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void firstLoad() {
        if (mPresenter != null) {
            mPresenter.getCanUseredFingerprintsList();
        }
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
        List<PetiyaakBoxInfo>list = (List<PetiyaakBoxInfo>) respone.data;
        if (list != null && list.size() > 0) {
            infos.clear();
            infos.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void fail(Throwable error, Integer code, String msg) {

    }
}
