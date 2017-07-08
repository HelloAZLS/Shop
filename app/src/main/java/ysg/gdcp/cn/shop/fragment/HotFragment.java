package ysg.gdcp.cn.shop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.Utils.Contants;
import ysg.gdcp.cn.shop.Utils.Pager;
import ysg.gdcp.cn.shop.activity.WareDetailActicity;
import ysg.gdcp.cn.shop.adapter.BaseAdapter;
import ysg.gdcp.cn.shop.adapter.DividerItemDecortion;
import ysg.gdcp.cn.shop.adapter.HWAdapter;
import ysg.gdcp.cn.shop.bean.Page;
import ysg.gdcp.cn.shop.bean.Wares;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {


    private View mView;
    private MaterialRefreshLayout mRefresh;
    private RecyclerView mRecyclerView;
    private HWAdapter mHotWaresAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_hot, container, false);
        initViews();

        Pager pager = Pager.newBuilder().setUrl(Contants.API.WARES_HOT).setLoadMore(true).setOnPageListener(new Pager.OnPageListener() {
            @Override
            public void load(List datas, int totalPage, int totalCount) {

                mHotWaresAdapter = new HWAdapter(datas, getContext());
                mHotWaresAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void OnClick(View view, int position) {
                        Wares wares = mHotWaresAdapter.getItem(position);
                        Intent intent =new Intent(getActivity(), WareDetailActicity.class);
                        intent.putExtra(Contants.WARES_ID,wares);
                        startActivity(intent);
                    }
                });
                mRecyclerView.setAdapter(mHotWaresAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecortion(getContext(), DividerItemDecortion.VERTICAL_LIST));
            }

            @Override
            public void refresh(List datas, int totalPage, int totalCount) {
                mHotWaresAdapter.clearData();
                mHotWaresAdapter.addData(datas);
                mRecyclerView.scrollToPosition(0);

            }

            @Override
            public void loadMore(List datas, int totalPage, int totalCount) {
                mHotWaresAdapter.addData(mHotWaresAdapter.getData().size(), datas);
                mRecyclerView.scrollToPosition(mHotWaresAdapter.getData().size());
            }
        }).setPageSize(20).setRefreshLayout(mRefresh).bulider(getContext(), new TypeToken<Page<Wares>>() {
        }.getType());
        pager.request();

        return mView;

    }

    private void initViews() {
        mRefresh = (MaterialRefreshLayout) mView.findViewById(R.id.refresh);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.hot_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }


}
