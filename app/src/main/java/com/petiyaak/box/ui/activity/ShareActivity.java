package com.petiyaak.box.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.petiyaak.box.R;
import com.petiyaak.box.adapter.ShareListAdapter;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.SharePresenter;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.IShareView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShareActivity extends BaseActivity<SharePresenter> implements IShareView {

    @BindView(R.id.share_back)
    ImageView shareBack;
    @BindView(R.id.share_search)
    ImageView shareSearch;
    @BindView(R.id.share_edit)
    EditText shareEdit;
    @BindView(R.id.share_list)
    RecyclerView shareList;
    List<UserInfo> userInfos = new ArrayList<>();
    private ShareListAdapter mAdapter;

    private PetiyaakBoxInfo info;

    @Override
    protected int getContentView() {
        return R.layout.activity_share;
    }

    @Override
    public void initData() {

        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        shareList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShareListAdapter(userInfos, true);
        shareList.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        shareBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shareSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = shareEdit.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showToast("not empty");
                    return;
                }
                mPresenter.getUserInfoList(name);
            }
        });

        mAdapter.addChildClickViewIds(R.id.share_submit);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.share_submit) {
//                    DialogUtil.shareToUser(ShareActivity.this, userInfos.get(position).getUsername(), new OnDialogClick() {
//                        @Override
//                        public void onDialogOkClick(String value) {
//                            mPresenter.shareToUser(userInfos.get(position).getId(),2);
//                        }
//
//                        @Override
//                        public void onDialogCloseClick(String value) {
//
//                        }
//                    });

                    Intent intent = new Intent(ShareActivity.this, FingerActivity.class);
                    intent.putExtra(ConstantEntiy.INTENT_BOX,info);
                    intent.putExtra(ConstantEntiy.INTENT_USER,userInfos.get(position));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected SharePresenter createPresenter() {
        return new SharePresenter();
    }


    @Override
    public void success(BaseRespone respone) {
        List<UserInfo> list = (List<UserInfo>)respone.getData();
        if (list.size() > 0) {
            userInfos.clear();
            userInfos.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void fail(Throwable error, Integer code, String msg) {
        ToastUtils.showToast(msg+"");
    }
}
