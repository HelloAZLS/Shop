package ysg.gdcp.cn.shop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.Utils.Contants;
import ysg.gdcp.cn.shop.listener.OkHttpBaseCallBack;
import ysg.gdcp.cn.shop.Utils.OkHttpUtils;
import ysg.gdcp.cn.shop.listener.SpotsCallBack;
import ysg.gdcp.cn.shop.adapter.DividerItemDecortion;
import ysg.gdcp.cn.shop.adapter.HomeCategoryAdapter;
import ysg.gdcp.cn.shop.bean.Banner;
import ysg.gdcp.cn.shop.bean.Campaign;
import ysg.gdcp.cn.shop.bean.HomeCampaign;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private SliderLayout mSlider;
    private PagerIndicator mIndicator;
    private View mView;
    private RecyclerView mRecyclerView;
    private HomeCategoryAdapter mHomeCategoryAdapter;
    private Gson mGson;
    private List<Banner> mBanners;
    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
    private SwipeRefreshLayout mRefreshLayout;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        requestImages();
        initRecycleriew();
        initRefreshLayout();
        return mView;
    }

    public void initRefreshLayout(){
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initViews() {
        mSlider = (SliderLayout) mView.findViewById(R.id.slider);
        mIndicator = (PagerIndicator) mView.findViewById(R.id.custom_indicator);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refreshlayout);
    }

    protected void requestImages() {
        String url = Contants.API.BANNER_URL;
        okHttpUtils.get(url, new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onSucess(Response response, List<Banner> banners) {
                mBanners = banners;
                initSliderData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void initRecycleriew() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        okHttpUtils.get(Contants.API.CAMPAIGN_HOME, new OkHttpBaseCallBack<List<HomeCampaign>>() {

            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSucess(Response response, List<HomeCampaign> homeCampaigns) {
                initRecyclerViewData(homeCampaigns);
            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private void initRecyclerViewData(List<HomeCampaign> homeCampaigns) {

        mHomeCategoryAdapter = new HomeCategoryAdapter(homeCampaigns, getActivity());
        mHomeCategoryAdapter.setOnCampaignClickListener(new HomeCategoryAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {
                Toast.makeText(getContext(), "title" + campaign.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mHomeCategoryAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecortion(getContext(),DividerItemDecortion.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    private void initSliderData() {

        if (mBanners != null) {
            for (final Banner banner : mBanners) {
                TextSliderView textSliderView = new TextSliderView(this.getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSlider.addSlider(textSliderView);
                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        Toast.makeText(getActivity(), banner.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


        mSlider.setCustomIndicator(mIndicator);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSlider.setDuration(3000);
        mSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
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

}
