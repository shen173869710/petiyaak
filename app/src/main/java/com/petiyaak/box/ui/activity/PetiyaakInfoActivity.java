package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.jakewharton.rxbinding2.view.RxView;
import com.petiyaak.box.R;
import com.petiyaak.box.adapter.ShareListAdapter;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.base.BaseApp;
import com.petiyaak.box.constant.ConstantEntiy;
import com.petiyaak.box.customview.OnDialogClick;
import com.petiyaak.box.event.BindSucessEvent;
import com.petiyaak.box.event.ShareSucessEvent;
import com.petiyaak.box.model.bean.PetiyaakBoxInfo;
import com.petiyaak.box.model.bean.UserInfo;
import com.petiyaak.box.model.respone.BaseRespone;
import com.petiyaak.box.presenter.PetiyaakInfoPresenter;
import com.petiyaak.box.util.DialogUtil;
import com.petiyaak.box.util.LogUtils;
import com.petiyaak.box.util.ToastUtils;
import com.petiyaak.box.view.IPetiyaakInfoView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class PetiyaakInfoActivity extends BaseActivity <PetiyaakInfoPresenter> implements IPetiyaakInfoView {

    @BindView(R.id.main_title_back)
    RelativeLayout mainTitleBack;
    @BindView(R.id.main_title_title)
    TextView mainTitleTitle;
    @BindView(R.id.main_title_right)
    RelativeLayout mainTitleRight;
    @BindView(R.id.info_icon)
    CircleImageView infoIcon;
    @BindView(R.id.petiyaak_info_name)
    TextView petiyaakInfoName;
    @BindView(R.id.petiyaak_info_status)
    TextView petiyaakInfoStatus;
    @BindView(R.id.info_blue)
    TextView infoBlue;
    @BindView(R.id.info_finger)
    TextView infoFinger;
    @BindView(R.id.info_option)
    TextView infoOption;
    @BindView(R.id.info_share)
    TextView infoShare;
    @BindView(R.id.info_list)
    RecyclerView infoList;

    private PetiyaakBoxInfo info;
    List<UserInfo> userInfos = new ArrayList<>();
    private ShareListAdapter mAdapter;

    private int cuttentPostion;
    @Override
    protected int getContentView() {
        return R.layout.activity_petiyaak_info;
    }

    @Override
    public void initData() {
        info = (PetiyaakBoxInfo) getIntent().getSerializableExtra(ConstantEntiy.INTENT_BOX);
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleTitle.setText("My petiyaakInfo Box");
        petiyaakInfoName.setText(info.getDeviceName());
        if (info.isItemBlueStatus()) {
            petiyaakInfoStatus.setText(R.string.connect);
            petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.dark));
        }else {
            petiyaakInfoStatus.setText(R.string.disconnect);
            petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.black));
        }
        infoList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShareListAdapter(userInfos, false);
        infoList.setAdapter(mAdapter);
        if (info.getDeviceId() > 0 ) {
            mPresenter.getUserListByDeviceId(info.getDeviceId(),true);
        }

        BluetoothClient mClient = new BluetoothClient(this);
        if (!TextUtils.isEmpty(info.getBluetoothMac())) {
            BleConnectOptions options = new BleConnectOptions.Builder()
                    .setConnectRetry(3)   // 连接如果失败重试3次
                    .setConnectTimeout(30000)   // 连接超时30s
                    .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                    .setServiceDiscoverTimeout(20000)  // 发现服务超时20s
                    .build();
            mClient.clearRequest(info.getBluetoothMac(), Constants.REQUEST_READ);
            mClient.clearRequest(info.getBluetoothMac(), Constants.REQUEST_WRITE);
            mClient.connect(info.getBluetoothMac(), options,new BleConnectResponse() {
                @Override
                public void onResponse(int code, BleGattProfile profile) {
                    LogUtils.e("PetiyaakInfoActivity", "connect->onResponse"+code);
                    if (code == REQUEST_SUCCESS) {
                        info.setItemBlueStatus(true);
                    }else {
                        info.setItemBlueStatus(false);
                    }
                    if (info.isItemBlueStatus()) {
                        petiyaakInfoStatus.setText(R.string.connect);
                        petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.dark));
                    }else {
                        petiyaakInfoStatus.setText(R.string.disconnect);
                        petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.black));
                    }
                }
            });
        }
    }

    @Override
    public void initListener() {
        RxView.clicks(mainTitleBack).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });

        RxView.clicks(infoBlue).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

                if (!TextUtils.isEmpty(info.getBluetoothMac())) {
                    ToastUtils.showToast("Bluetooth device already bound");
                    return;
                }
                Intent intent = new Intent(PetiyaakInfoActivity.this, BindPetiyaakActivity.class);
                intent.putExtra(ConstantEntiy.INTENT_BOX, info);
                startActivity(intent);
            }
        });


        RxView.clicks(infoFinger).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (TextUtils.isEmpty(info.getBluetoothMac())) {
                    ToastUtils.showToast("No Bluetooth device");
                    return;
                }
                startActivity(FingerActivity.startIntent(PetiyaakInfoActivity.this,info,BaseApp.userInfo,true));
            }
        });

        RxView.clicks(infoShare).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (TextUtils.isEmpty(info.getBluetoothMac())) {
                    ToastUtils.showToast("No Bluetooth device");
                    return;
                }
                Intent intent = new Intent(PetiyaakInfoActivity.this, ShareActivity.class);
                intent.putExtra(ConstantEntiy.INTENT_BOX, info);
                startActivity(intent);
            }
        });

        RxView.clicks(infoOption).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (TextUtils.isEmpty(info.getBluetoothMac())) {
                    ToastUtils.showToast("No Bluetooth device");
                    return;
                }
                Intent intent = new Intent(PetiyaakInfoActivity.this, OptionActivity.class);
                intent.putExtra(ConstantEntiy.INTENT_BOX, info);
                startActivity(intent);
            }
        });

        mAdapter.addChildClickViewIds(R.id.share_submit);
        mAdapter.addChildClickViewIds(R.id.share_bind);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                UserInfo userInfo = userInfos.get(position);
                if ( view.getId() == R.id.share_submit ) {
                    DialogUtil.cancleToUser(PetiyaakInfoActivity.this, userInfo.getUsername(), new OnDialogClick() {
                        @Override
                        public void onDialogOkClick(String value) {
                          cuttentPostion = position;
                          startActivity( FingerDelActivity.startIntent(PetiyaakInfoActivity.this,info,userInfo,false));
                        }
                        @Override
                        public void onDialogCloseClick(String value) {

                        }
                    });
                } else if (view.getId() == R.id.share_bind) {
                    startActivity(FingerActivity.startIntent(PetiyaakInfoActivity.this,info,userInfo,false));
                }
            }
        });
    }

    @Override
    protected PetiyaakInfoPresenter createPresenter() {
        return new PetiyaakInfoPresenter();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBindSuccess(BindSucessEvent event) {
        if (event != null) {
            info.setBluetoothName(event.getInfo().getBluetoothName());
            info.setItemBlueStatus(true);
            info.setBluetoothMac(event.getInfo().getBluetoothMac());
            petiyaakInfoName.setText(info.getDeviceName());
            if (info.isItemBlueStatus()) {
                petiyaakInfoStatus.setText(R.string.connect);
                petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.dark));
            }else {
                petiyaakInfoStatus.setText(R.string.disconnect);
                petiyaakInfoStatus.setTextColor(getResources().getColor(R.color.black));
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareSucessEvent(ShareSucessEvent event) {
        mPresenter.getUserListByDeviceId(info.getDeviceId(),false);
    }

    @Override
    public void success(BaseRespone respone) {
        List<UserInfo> list = (List<UserInfo> )respone.getData();
        if (list != null && list.size() > 0) {
            userInfos.clear();
            userInfos.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void fail(Throwable error, Integer code, String msg) {
        ToastUtils.showToast(""+msg);
    }

}
