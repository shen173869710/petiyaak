package com.petiyaak.box.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.petiyaak.box.R;
import com.petiyaak.box.base.BaseFragment;
import com.petiyaak.box.presenter.BasePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenzhaolin on 2019/7/10.
 */

public class HistoryFragment extends BaseFragment {

    @BindView(R.id.main_title_back)
    RelativeLayout mainTitleBack;
    @BindView(R.id.main_title_title)
    TextView mainTitleTitle;
    @BindView(R.id.main_title_right)
    RelativeLayout mainTitleRight;
    @BindView(R.id.history_tabs)
    SlidingTabLayout historyTabs;
    @BindView(R.id.history_viewPager)
    ViewPager historyViewPager;
    @BindView(R.id.history_view_1)
    View historyView1;
    @BindView(R.id.history_view_2)
    View historyView2;
    @BindView(R.id.history_view_3)
    View historyView3;


    private View view;
    Unbinder unbinder;

    String[] titles = {
            "All",
            "Bluetooth",
            "Finger Print"
    };

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_history, null);
    }

    @Override
    protected void initView() {
        view = mView.findViewById(R.id.main_title_bar);
        TextView title = view.findViewById(R.id.main_title_title);
        title.setText(R.string.main_tab_3);

        fragments.add(HistoryAllFragment.newInstance());
        fragments.add(HistoryAllFragment.newInstance());
        fragments.add(HistoryAllFragment.newInstance());

        historyTabs.setViewPager(historyViewPager, titles, (AppCompatActivity) mContext, fragments);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initListener() {
        showView(0);
        historyTabs.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showView(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        historyViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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


    public void showView (int postion) {
        historyView1.setVisibility(View.INVISIBLE);
        historyView2.setVisibility(View.INVISIBLE);
        historyView3.setVisibility(View.INVISIBLE);
        if (postion == 0) {
            historyView1.setVisibility(View.VISIBLE);
        }else if (postion == 1) {
            historyView2.setVisibility(View.VISIBLE);
        }else if (postion == 2) {
            historyView3.setVisibility(View.VISIBLE);
        }
    }

}
