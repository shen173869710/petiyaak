package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.jakewharton.rxbinding2.view.RxView;
import com.petiyaak.box.R;
import com.petiyaak.box.adapter.UserListAdapter;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.customview.OnDialogClick;
import com.petiyaak.box.model.bean.FingerInfo;
import com.petiyaak.box.presenter.BasePresenter;
import com.petiyaak.box.util.DialogUtil;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class BindFingerActivity extends BaseActivity {

    @BindView(R.id.main_title_back)
    RelativeLayout mainTitleBack;
    @BindView(R.id.main_title_title)
    TextView mainTitleTitle;
    @BindView(R.id.main_title_right)
    RelativeLayout mainTitleRight;
    @BindView(R.id.finger_add)
    LinearLayout fingerAdd;
    @BindView(R.id.finger_list)
    RecyclerView fingerList;

    private List<FingerInfo> infos = new ArrayList<>();
    private UserListAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_finger;
    }

    @Override
    public void initData() {
        mainTitleTitle.setText("Add Finger");
        mainTitleBack.setVisibility(View.VISIBLE);

        fingerList.setLayoutManager(new LinearLayoutManager(this));
        fingerList.addItemDecoration(new SpacesItemDecoration(40));
        mAdapter = new UserListAdapter(infos);
        fingerList.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {

        RxView.clicks(mainTitleBack).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });

        RxView.clicks(fingerAdd).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                DialogUtil.addUser(BindFingerActivity.this, new OnDialogClick() {
                    @Override
                    public void onDialogOkClick(String value) {
                        FingerInfo info = new FingerInfo();
                        info.userName = value;
                        info.leftFinger = true;
                        infos.add(info);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onDialogCloseClick(String value) {

                    }
                });
            }
        });


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(BindFingerActivity.this, UserInfoActivity.class);
                intent.putExtra("user", infos.get(position));
                startActivity(intent);
            }
        });





        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                DialogUtil.delUser(BindFingerActivity.this, new OnDialogClick() {
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

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

}
