package com.petiyaak.box.ui.activity;

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
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.customview.OnDialogClick;
import com.petiyaak.box.event.ShareSucessEvent;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.ShareUserPresenter;
import com.petiyaak.box.util.DialogUtil;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.IShareUserView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

public class ShareActivity extends BaseActivity<ShareUserPresenter> implements IShareUserView {

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
                    DialogUtil.shareToUser(ShareActivity.this, userInfos.get(position).getUsername(), new OnDialogClick() {
                        @Override
                        public void onDialogOkClick(String value) {
                            mPresenter.shareToUser(userInfos.get(position).getId(), info);
                        }

                        @Override
                        public void onDialogCloseClick(String value) {

                        }
                    });
                }
            }
        });
    }

    @Override
    protected ShareUserPresenter createPresenter() {
        return new ShareUserPresenter();
    }

    @Override
    public void shareSuccess(BaseRespone respone) {
        EventBus.getDefault().post(new ShareSucessEvent());
        ToastUtils.showToast("share success ");
        finish();
    }

    @Override
    public void shareFail(Throwable error, Integer code, String msg) {

    }

    @Override
    public void serachSuccess(BaseRespone respone) {
        List<UserInfo> list = (List<UserInfo>)respone.getData();
        int size = list.size();
        if (size > 0) {
            Iterator<UserInfo> it = list.iterator();
            while (it.hasNext()){
                if (BaseApp.userInfo != null) {
                    int id = BaseApp.userInfo.getId();
                    UserInfo user = it.next();
                    if (id == user.getId()) {
                        it.remove();
                    }
                }
            }
            userInfos.clear();
            userInfos.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void serachFail(Throwable error, Integer code, String msg) {

    }
}
