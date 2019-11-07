package com.petiyaak.box.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.petiyaak.box.R;
import com.petiyaak.box.adapter.PetiyaakListAdapter;
import com.petiyaak.box.base.BaseFragment;
import com.petiyaak.box.customview.DialogContent;
import com.petiyaak.box.customview.InputDialog;
import com.petiyaak.box.customview.OnDialogClick;
import com.petiyaak.box.customview.OptionDialog;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.presenter.BasePresenter;
import com.petiyaak.box.ui.activity.PetiyaakInfoActivity;
import com.petiyaak.box.util.DialogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenzhaolin on 2019/7/10.
 */

public class PetiyaakFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.petiyaak_list)
    RecyclerView petiyaakList;
    PetiyaakListAdapter mAdapter;

    private View view;
    private ArrayList<PetiyaakBoxInfo> infos = new ArrayList<>();
    public static PetiyaakFragment newInstance() {
        PetiyaakFragment fragment = new PetiyaakFragment();
        return fragment;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_petiyaak, null);
    }

    @Override
    protected void initView() {
        view = mView.findViewById(R.id.main_title_bar);
        TextView title = view.findViewById(R.id.main_title_title);
        title.setText(R.string.main_tab_1);
        petiyaakList.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        infos.add(new PetiyaakBoxInfo(0));
        mAdapter = new PetiyaakListAdapter(infos);
        petiyaakList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {


                    DialogUtil.addPetiyaak(mContext,new OnDialogClick() {
                        @Override
                        public void onDialogOkClick(String value) {
                            PetiyaakBoxInfo info = new PetiyaakBoxInfo(1);
                            info.setItemName(value);
                            infos.add(info);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onDialogCloseClick(String value) {
                            DialogUtil.delPetiyaak(mContext, new OnDialogClick() {
                                @Override
                                public void onDialogOkClick(String value) {
                                    infos.remove(position);
                                    mAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onDialogCloseClick(String value) {

                                }
                            });
                        }
                    });
                }else {
                    mContext.startActivity(new Intent(mContext, PetiyaakInfoActivity.class));
                }
            }
        });
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


}
