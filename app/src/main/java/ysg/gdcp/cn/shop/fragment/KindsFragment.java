package ysg.gdcp.cn.shop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.Utils.Contants;
import ysg.gdcp.cn.shop.Utils.OkHttpUtils;
import ysg.gdcp.cn.shop.adapter.BaseAdapter;
import ysg.gdcp.cn.shop.adapter.CategoryAdapter;
import ysg.gdcp.cn.shop.adapter.DividerGridItemDecoration;
import ysg.gdcp.cn.shop.adapter.DividerItemDecortion;
import ysg.gdcp.cn.shop.adapter.WaresAdapter;
import ysg.gdcp.cn.shop.bean.Banner;
import ysg.gdcp.cn.shop.bean.Category;
import ysg.gdcp.cn.shop.bean.Page;
import ysg.gdcp.cn.shop.bean.Wares;
import ysg.gdcp.cn.shop.listener.OkHttpBaseCallBack;
import ysg.gdcp.cn.shop.listener.SpotsCallBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class KindsFragment extends Fragment {


    private View mView;
    private RecyclerView mCategoryRecyclerView;
    private RecyclerView mWaresRecyclerView;
    private SliderLayout mSliderLayout;
    private OkHttpUtils mOkHttpUtils = OkHttpUtils.getInstance();
    private CategoryAdapter mCategoryAdapter;
    private WaresAdapter mWaresAdapter;
    private MaterialRefreshLayout mRefreshLayout;

    public static final int STATE_NORMAL = 0;
    public static final int STATE_REDRESH = 1;
    public static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;
    private long category_id = 0;
    private int totaPage = 1;
    private int currentPage = 1;
    private int pageSize = 10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_kinds, null, false);

        initViews();

        requestCategoryData();
        requestBannerDatas();
        initRefreshLayout();
        return mView;
    }

    private void initViews() {

        mCategoryRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview_category);
        mRefreshLayout = (MaterialRefreshLayout) mView.findViewById(R.id.refresh_layout);
        mWaresRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview_wares);
        mSliderLayout = (SliderLayout) mView.findViewById(R.id.slider_kinds);
    }

    private void initRefreshLayout() {

        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                refreshData();

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                if (currentPage <= totaPage)
                    loadMore();
                else {

                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refreshData() {
        currentPage = 1;
        state = STATE_REDRESH;
        requestWares(category_id);


    }

    private void loadMore() {
        currentPage = ++currentPage;
        state = STATE_MORE;
        requestWares(category_id);

    }

    public void requestCategoryData() {
        mOkHttpUtils.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {


            @Override
            public void onSucess(Response response, List<Category> categories) {

                showCategoryData(categories);
                if (categories != null && categories.size() > 0) {
                    category_id = categories.get(0).getId();
                    requestWares(category_id);
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    public void showCategoryData(List<Category> categorys) {
        mCategoryAdapter = new CategoryAdapter(categorys, getContext());
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Category category = mCategoryAdapter.getItem(position);
                category_id = category.getId();
                currentPage = 1;
                state = STATE_NORMAL;

                requestWares(category.getId());
            }
        });
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCategoryRecyclerView.addItemDecoration(new DividerItemDecortion(getContext(), DividerItemDecortion.VERTICAL_LIST));
    }

    private void requestBannerDatas() {

        String url = Contants.API.BANNER + "?type=1";
        mOkHttpUtils.get(url, new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onSucess(Response response, List<Banner> banners) {
                showSliderData(banners);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showSliderData(List<Banner> mBanners) {

        if (mBanners != null) {
            for (final Banner banner : mBanners) {
                DefaultSliderView textSliderView = new DefaultSliderView(this.getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);
                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        Toast.makeText(getActivity(), banner.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateDown);
        mSliderLayout.setDuration(3000);
        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void requestWares(long categoryId) {
//        String url = Contants.API.WARES_LIST + "?categoryId=" + categoryId + "&curPage=" + currentPage + "&pageSize" + pageSize;
        String url = Contants.API.WARES_LIST + "?categoryId=" + categoryId + "&curPage=" + currentPage + "&pageSize=" + pageSize;
        mOkHttpUtils.get(url, new OkHttpBaseCallBack<Page<Wares>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSucess(Response response, Page<Wares> waresPage) {
                currentPage = waresPage.getCurrentPage();
                totaPage = waresPage.getTotalPage();
                showWaresDatas(waresPage.getList());
            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    public void showWaresDatas(List<Wares> wares) {

        switch (state) {
            case STATE_NORMAL:
                if (mWaresAdapter == null) {
                    mWaresAdapter = new WaresAdapter(getContext(), wares);

                    mWaresRecyclerView.setAdapter(mWaresAdapter);
                    mWaresRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    mWaresRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mWaresRecyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
                } else {
                    mWaresAdapter.clearData();
                    mWaresAdapter.addData(wares);
                }
                break;
            case STATE_REDRESH:
                mWaresAdapter.clearData();
                mWaresAdapter.addData(wares);
                mWaresRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                mWaresAdapter.addData(mWaresAdapter.getData().size(), wares);
                mWaresRecyclerView.scrollToPosition(mWaresAdapter.getData().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }


    }


}
