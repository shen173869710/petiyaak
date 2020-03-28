package com.petiyaak.box.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.petiyaak.box.R;
import com.petiyaak.box.adapter.HistoryAllAdapter;
import com.petiyaak.box.base.BaseFragment;
import com.petiyaak.box.model.bean.FingerInfo;
import com.petiyaak.box.presenter.BasePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenzhaolin on 2019/7/10.
 */

public class HistoryAllFragment extends BaseFragment {

    @BindView(R.id.setting_list)
    RecyclerView settingList;
    private HistoryAllAdapter adapter;

    Unbinder unbinder;

    private ArrayList<FingerInfo>settingItems = new ArrayList<>();

    public static HistoryAllFragment newInstance() {
        HistoryAllFragment fragment = new HistoryAllFragment();
        return fragment;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_history_all, null);
    }

    @Override
    protected void initView() {
        settingList.setLayoutManager(new LinearLayoutManager(mContext));
        settingList.addItemDecoration(new SpacesItemDecoration(10));
        settingItems.add(new FingerInfo());
        settingItems.add(new FingerInfo());
        settingItems.add(new FingerInfo());
        settingItems.add(new FingerInfo());
        settingItems.add(new FingerInfo());
        settingItems.add(new FingerInfo());
        settingItems.add(new FingerInfo());
        settingItems.add(new FingerInfo());
        settingItems.add(new FingerInfo());
        settingItems.add(new FingerInfo());
        adapter = new HistoryAllAdapter(settingItems);
        settingList.setAdapter(adapter);
    }

    @Override
    protected void initData() {

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

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            // Add top margin only for the first item to avoid double space between items
            outRect.top = space;
        }
    }

}
