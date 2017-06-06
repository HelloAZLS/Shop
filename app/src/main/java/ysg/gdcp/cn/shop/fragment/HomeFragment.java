package ysg.gdcp.cn.shop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.List;

import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.Utils.OkHttpUtils;
import ysg.gdcp.cn.shop.Utils.SpotsCallBack;
import ysg.gdcp.cn.shop.adapter.DividerItemDecortion;
import ysg.gdcp.cn.shop.adapter.HomeCategoryAdapter;
import ysg.gdcp.cn.shop.bean.Banner;
import ysg.gdcp.cn.shop.bean.HomeCategory;


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

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mSlider = (SliderLayout) mView.findViewById(R.id.slider);
        mIndicator = (PagerIndicator) mView.findViewById(R.id.custom_indicator);
//        initSliderData();
        requestImages();
        initRecycleriew();
        return mView;
    }

    protected void requestImages() {
        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";
//        mGson = new Gson();
//
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body =new FormEncodingBuilder()
//                .add("type","1")
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                String result = response.body().string();
//                Type type = new TypeToken<List<Banner>>() {
//                }.getType();
//                mBanners = mGson.fromJson(result, type);
//                initSliderData();
//
//            }
//        });

        okHttpUtils.get(url, new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onSucess(Response response, List<Banner> banners) {
                mBanners=banners;
                initSliderData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
        }

    private void initRecycleriew() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
        List<HomeCategory> categorys = new ArrayList<>();

        HomeCategory category = new HomeCategory("热门活动", R.drawable.img_big_1, R.drawable.img_1_small1, R.drawable.img_1_small2);
        categorys.add(category);

        category = new HomeCategory("有利可图", R.drawable.img_big_4, R.drawable.img_4_small1, R.drawable.img_4_small2);
        categorys.add(category);
        category = new HomeCategory("品牌街", R.drawable.img_big_2, R.drawable.img_2_small1, R.drawable.img_2_small2);
        categorys.add(category);

        category = new HomeCategory("金融街，包赚翻", R.drawable.img_big_1, R.drawable.img_3_small1, R.drawable.imag_3_small2);
        categorys.add(category);

        category = new HomeCategory("超值购", R.drawable.img_big_0, R.drawable.img_0_small1, R.drawable.img_0_small2);
        categorys.add(category);
        mHomeCategoryAdapter = new HomeCategoryAdapter(categorys);
        mRecyclerView.setAdapter(mHomeCategoryAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecortion());
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
