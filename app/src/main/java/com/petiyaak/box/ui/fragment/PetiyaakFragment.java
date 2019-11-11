package com.petiyaak.box.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
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
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.customview.OnDialogClick;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.presenter.BasePresenter;
import com.petiyaak.box.ui.activity.PetiyaakInfoActivity;
import com.petiyaak.box.util.DialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
        EventBus.getDefault().register(this);
        view = mView.findViewById(R.id.main_title_bar);
        TextView title = view.findViewById(R.id.main_title_title);
        title.setText(R.string.main_tab_1);
        petiyaakList.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        petiyaakList.addItemDecoration(new SpacesItemDecoration(40));
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
                            info.setItemUserName(value);
                            infos.add(info);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onDialogCloseClick(String value) {

                        }
                    });
                }else {
                    Intent intent = new Intent(mContext, PetiyaakInfoActivity.class);
                    intent.putExtra(ConstantEntiy.INTENT_BOX, infos.get(position));
                    mContext.startActivity(intent);
                }
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
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
                return false;
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
        EventBus.getDefault().unregister(this);
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            // Add top margin only for the first item to avoid double space between items
            outRect.right = space;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPetiyaakBoxInfo(PetiyaakBoxInfo event) {
        if (event != null) {
            int size = infos.size();
            for (int i = 0; i < size; i++) {
                PetiyaakBoxInfo info = infos.get(i);
                if (event.getItemUserName().equals(info.getItemUserName())) {
                    info.setItemBlueName(event.getItemBlueName());
                    info.setItemBlueStatus(event.isItemBlueStatus());
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

}
