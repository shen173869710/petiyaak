package com.petiyaak.box.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseFragment;
import com.petiyaak.box.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenzhaolin on 2019/7/10.
 */

public class PetiyaakFragment extends BaseFragment  {


    Unbinder unbinder;

    private View view;

    public static PetiyaakFragment newInstance(){
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


}
