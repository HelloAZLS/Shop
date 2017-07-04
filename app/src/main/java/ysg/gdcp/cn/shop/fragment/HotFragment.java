package ysg.gdcp.cn.shop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.squareup.okhttp.Response;

import java.util.List;

import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.Utils.Contants;
import ysg.gdcp.cn.shop.Utils.OkHttpUtils;
import ysg.gdcp.cn.shop.adapter.BaseAdapter;
import ysg.gdcp.cn.shop.adapter.DividerItemDecortion;
import ysg.gdcp.cn.shop.adapter.HWAdapter;
import ysg.gdcp.cn.shop.bean.Page;
import ysg.gdcp.cn.shop.bean.Wares;
import ysg.gdcp.cn.shop.listener.SpotsCallBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {


    private View mView;
    private MaterialRefreshLayout mRefresh;
    private RecyclerView mRecyclerView;
    private OkHttpUtils mOkHttpUtils = OkHttpUtils.getInstance();
    private int currentPage = 1;
    private int pageSize = 10;
    private List<Wares> mDatas;
    private HWAdapter mHotWaresAdapter;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_REDRESH = 1;
    public static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;
    private int totaPage = 1;


    public HotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_hot, container, false);
        initViews();
        initRefreshLayout();
        getDatas();
        return mView;

    }

    private void initRefreshLayout() {

        mRefresh.setLoadMore(true);
        mRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                refreshData();

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                if (currentPage <= totaPage)
                    loadMore();
                else {

                    mRefresh.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refreshData() {
        currentPage = 1;
        state = STATE_REDRESH;
        getDatas();


    }

    private void loadMore() {
        currentPage = ++currentPage;
        state = STATE_MORE;
        getDatas();

    }

    private void getDatas() {
//        http://112.124.22.238:8081/course_api/wares/hot?currPage=1&pageSize=10
        String url = Contants.API.WARES_HOT + "?curPage=" + currentPage + "&pageSize=" + pageSize;
        mOkHttpUtils.get(url, new SpotsCallBack<Page<Wares>>(getContext()) {


            @Override
            public void onSucess(Response response, Page<Wares> waresPage) {
                mDatas = waresPage.getList();
                for (Wares data : mDatas) {
                    Log.i("数据", data.getName());
                }
                currentPage = waresPage.getCurrentPage();
                totaPage = waresPage.getTotalPage();
                showDatas();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    public void showDatas() {

        switch (state) {
            case STATE_NORMAL:
                mHotWaresAdapter = new HWAdapter(mDatas,getContext());
                mHotWaresAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void OnClick(View view, int position) {

                    }
                });
                mRecyclerView.setAdapter(mHotWaresAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecortion(getContext(), DividerItemDecortion.VERTICAL_LIST));

                break;
            case STATE_REDRESH:
                mHotWaresAdapter.clearData();
                mHotWaresAdapter.addData(mDatas);
                mRecyclerView.scrollToPosition(0);
                mRefresh.finishRefresh();
                break;
            case STATE_MORE:
                mHotWaresAdapter.addData(mHotWaresAdapter.getData().size(), mDatas);
                mRecyclerView.scrollToPosition(mHotWaresAdapter.getData().size());
                mRefresh.finishRefreshLoadMore();
                break;
        }


    }

    private void initViews() {
        mRefresh = (MaterialRefreshLayout) mView.findViewById(R.id.refresh);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.hot_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }


}
