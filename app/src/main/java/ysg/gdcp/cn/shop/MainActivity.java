package ysg.gdcp.cn.shop;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ysg.gdcp.cn.shop.bean.Tab;
import ysg.gdcp.cn.shop.fragment.CarFragment;
import ysg.gdcp.cn.shop.fragment.HomeFragment;
import ysg.gdcp.cn.shop.fragment.HotFragment;
import ysg.gdcp.cn.shop.fragment.KindsFragment;
import ysg.gdcp.cn.shop.fragment.MyFragment;
import ysg.gdcp.cn.shop.view.FragmentTabHost;
import ysg.gdcp.cn.shop.view.MyToolbar;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private List<Tab> mTabs;
    private int mCurrentTab = 0;
    private CarFragment carFragment;
    private MyToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initViews();
        initData();


    }

    private void initToolbar() {
        mToolbar =(MyToolbar)findViewById(R.id.toolbar_main);
    }

    private void initData() {
        mTabHost.setCurrentTab(mCurrentTab);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.fl_content);
        mTabs = new ArrayList<>();
        Tab home = new Tab(R.string.home, R.drawable.selector_icon_home, HomeFragment.class);
        Tab hot = new Tab(R.string.hot, R.drawable.selector_icon_hot, HotFragment.class);
        Tab kinds = new Tab(R.string.kinds, R.drawable.selector_icon_category, KindsFragment.class);
        Tab car = new Tab(R.string.car, R.drawable.selector_icon_cart, CarFragment.class);
        Tab my = new Tab(R.string.my, R.drawable.selector_icon_mine, MyFragment.class);
        mTabs.add(home);
        mTabs.add(hot);
        mTabs.add(kinds);
        mTabs.add(car);
        mTabs.add(my);
        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            View view = View.inflate(this, R.layout.tab_indicator, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.icon_tab);
            TextView tvTitle = (TextView) view.findViewById(R.id.txt_indicator);
            tvTitle.setText(tab.getTitle());
            imageView.setImageResource(tab.getId());
            tabSpec.setIndicator(view);
            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (s == getString(R.string.car)) {
                    refreshData();

                }else{
                    mToolbar.setVisibility(View.VISIBLE);
                }
            }
        });
        //去分割線
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);


    }

    private void refreshData() {
        if (carFragment == null) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.car));
            if (fragment != null) {
                carFragment = (CarFragment) fragment;
                carFragment.refreshData();

            }
        } else {
            carFragment.refreshData();
            carFragment.btnEditor();
        }

    }

    private void initViews() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
    }
}
