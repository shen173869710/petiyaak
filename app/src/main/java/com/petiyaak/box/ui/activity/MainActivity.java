package com.petiyaak.box.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.clj.fastble.BleManager;
import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseActivity;
import com.petiyaak.box.presenter.BasePresenter;
import com.petiyaak.box.ui.fragment.HistoryFragment;
import com.petiyaak.box.ui.fragment.PetiyaakFragment;
import com.petiyaak.box.ui.fragment.SettingFragment;
import com.petiyaak.box.ui.fragment.SharedFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenzhaolin on 2019/11/4.
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.bottomBar)
    RadioGroup bottomBar;
    @BindView(R.id.contentContainer)
    FrameLayout contentContainer;
    private List<Fragment> fragments;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    public void initData() {
        fragments = new ArrayList<>();
        fragments.add(PetiyaakFragment.newInstance());
        fragments.add(SharedFragment.newInstance());
        fragments.add(HistoryFragment.newInstance());
        fragments.add(SettingFragment.newInstance());
        showFragment(fragments.get(0));
    }

    @Override
    public void initListener() {
        bottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int position = 0;
                switch (i) {
                    case R.id.main_tab_petiyaak:
                        position = 0;
                        break;
                    case R.id.main_tab_shared:
                        position = 1;
                        break;
                    case R.id.main_tab_history:
                        position = 2;
                        break;
                    case R.id.main_tab_setting:
                        position = 3;
                        break;
                }
                showFragment(fragments.get(position));
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoingEvent(int i) {

    }

    private void showFragment(Fragment fragment) {
        if (null == fragment) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentTag = fragment.getClass().getSimpleName();
        addFragment(fragmentManager, fragment, fragmentTag);
        hideAllFragment(fragmentManager, fragment);
        fragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
    }


    private void addFragment(FragmentManager fm, Fragment fragment, String tag) {
        if (null != fm && !fm.isDestroyed()) {
            FragmentTransaction ft = fm.beginTransaction();
            fm.executePendingTransactions();
            if (!fragment.isAdded() && null == fm.findFragmentByTag(tag)) {
                ft.add(R.id.contentContainer, fragment, tag);
                ft.commitAllowingStateLoss();
            }
        }
    }

    /**
     * @param fm
     * @param fragment 隐藏 其他 fragment
     */
    private void hideAllFragment(FragmentManager fm, Fragment fragment) {
        if (null != fm && !fm.isDestroyed()) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment frag : fragments) {
                if (frag != fragment) {
                    ft.hide(frag);
                }
            }
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
    }
}
