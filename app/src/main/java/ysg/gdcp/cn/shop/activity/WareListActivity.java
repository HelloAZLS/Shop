package ysg.gdcp.cn.shop.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.Utils.Contants;
import ysg.gdcp.cn.shop.Utils.Pager;
import ysg.gdcp.cn.shop.adapter.HWAdapter;
import ysg.gdcp.cn.shop.bean.Page;
import ysg.gdcp.cn.shop.bean.Wares;
import ysg.gdcp.cn.shop.view.MyToolbar;

public class WareListActivity extends AppCompatActivity implements Pager.OnPageListener<Wares>, TabLayout.OnTabSelectedListener, View.OnClickListener {

    private TabLayout mTabLayout;
    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLayout;
    private MyToolbar mMyToolbar;
    private int orderBy = 0;
    private long CampaignId = 0;
    private Pager mPager;
    private HWAdapter mWaresAdapter;
    private TextView mTextView;

    public static final int TAG_DEFAULT = 0;
    public static final int TAG_SALE = 1;
    public static final int TAG_PRICE = 2;
    public static final int ACTION_LIST = 1;
    public static final int ACTION_GIRD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_list);
        CampaignId = getIntent().getLongExtra(Contants.COMPAINGAIN_ID, 0);
        Log.i("COMPAINGAIN_ID", CampaignId + "");
        initViews();
        initTab();
        getDatas();
    }

    private void initTab() {
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setText("默认");
        tab.setTag(TAG_DEFAULT);
        mTabLayout.addTab(tab);

        tab = mTabLayout.newTab();
        tab.setText("价格");
        tab.setTag(TAG_PRICE);
        mTabLayout.addTab(tab);

        tab = mTabLayout.newTab();
        tab.setText("销量 ");
        tab.setTag(TAG_PRICE);
        mTabLayout.addTab(tab);
        mTabLayout.setOnTabSelectedListener(this);
    }

    private void getDatas() {
        mPager = Pager.newBuilder().setUrl(Contants.API.WARES_CAMPAIN_LIST).putParams("campaignId", CampaignId).putParams("orderBy", orderBy)
                .setRefreshLayout(mRefreshLayout).setLoadMore(true).setOnPageListener(this).bulider(this, new TypeToken<Page<Wares>>() {
                }.getType());
        mPager.request();
    }

    private void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTextView = (TextView) findViewById(R.id.txt_summary);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh_layout);
        mMyToolbar = (MyToolbar) findViewById(R.id.toolbar);
        initToolbar();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initToolbar() {
        mMyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WareListActivity.this.finish();
            }
        });
        mMyToolbar.setRightButtionIcon(R.drawable.icon_grid_32);
        mMyToolbar.getRightButton().setTag(ACTION_LIST);
        mMyToolbar.setRightButtionONclickListener(this);
    }

    @Override
    public void load(List<Wares> datas, int totalPage, int totalCount) {
        mTextView.setText("共有" + totalCount + "件商品");

        if (mWaresAdapter == null) {
            mWaresAdapter = new HWAdapter(datas, this);
            mRecyclerView.setAdapter(mWaresAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        } else {
            mWaresAdapter.refreshData(datas);
        }
    }

    @Override
    public void refresh(List<Wares> datas, int totalPage, int totalCount) {
//        Log.i("datas",datas.size()+"");
        mWaresAdapter.refreshData(datas);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void loadMore(List<Wares> datas, int totalPage, int totalCount) {
        mWaresAdapter.loadMoreData(datas);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        orderBy = (int) tab.getTag();
        mPager.putParam("orderBy", orderBy);
        mPager.request();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        int action = (int) view.getTag();
        if (action == ACTION_LIST) {
            mMyToolbar.setRightButtionIcon(R.drawable.icon_list_32);
            mWaresAdapter.reSet(R.layout.template_grid_wares);
            mMyToolbar.getRightButton().setTag(ACTION_GIRD);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }else if (action==ACTION_GIRD){
            mMyToolbar.setRightButtionIcon(R.drawable.icon_grid_32);
            mWaresAdapter.reSet(R.layout.template_hot_wares);
            mMyToolbar.getRightButton().setTag(ACTION_LIST);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
