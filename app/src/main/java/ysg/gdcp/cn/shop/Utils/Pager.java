package ysg.gdcp.cn.shop.Utils;

import android.content.Context;
import android.util.Log;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ysg.gdcp.cn.shop.bean.Page;
import ysg.gdcp.cn.shop.listener.SpotsCallBack;


/**
 * Created by Administrator on 2017/7/5 19:12.
 *
 * @author ysg
 */

public class Pager {


    public static final int STATE_NORMAL = 0;
    public static final int STATE_REDRESH = 1;
    public static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;

    private OkHttpUtils mOkHttpUtils;
    private static Builder builder;

    private Pager() {
        mOkHttpUtils = OkHttpUtils.getInstance();
        initRefreshLayout();
    }

    public static Builder newBuilder() {
         builder=new Builder();
        return builder;
    }

    public void request(){
        requestDatas();
    }
    private void initRefreshLayout() {

        builder.mRefreshLayout.setLoadMore(builder.canLoadMore);
        builder.mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                refreshData();

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                if (builder.pageIndex <= builder.totaPage)
                    loadMore();
                else {

                    builder.mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refreshData() {
        builder.pageIndex = 1;
        state = STATE_REDRESH;
        requestDatas();


    }

    private void loadMore() {
        builder.pageIndex = ++builder.pageIndex ;
        state = STATE_MORE;
        requestDatas();

    }

    private void requestDatas() {
//        http://112.124.22.238:8081/course_api/wares/hot?currPage=1&pageSize=10
//        String url = Contants.API.WARES_HOT + "?curPage=" + currentPage + "&pageSize=" + pageSize;
        mOkHttpUtils.get(bulierUrl(), new RequestCallBack(builder.mContext));

    }


    private <T> void showDatas(List<T> datas, int totalPage, int totalCount) {

        switch (state) {
            case STATE_NORMAL:
                if (builder.mListener != null) {
                    builder.mListener.load(datas, totalPage, totalCount);
                }


                break;
            case STATE_REDRESH:
                if (builder.mListener != null) {
                    builder.mListener.refresh(datas, totalPage, totalCount);
                }
                builder.mRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                if (builder.mListener != null) {
                    builder.mListener.loadMore(datas, totalPage, totalCount);
                }
                builder.mRefreshLayout.finishRefreshLoadMore();
                break;
        }


    }
    private String bulierUrl() {
        Log.i("参数",builder.url + "?" + builerUrlParams());
        return builder.url + "?" + builerUrlParams();
    }

    private String builerUrlParams() {
        HashMap<String, Object> map = builder.params;
        map.put("curPage", builder.pageIndex);
        map.put("pageSize", builder.pageSize);

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public static class Builder {


        private String url;
        private MaterialRefreshLayout mRefreshLayout;
        private boolean canLoadMore;
        private int totaPage = 1;
        private int pageSize = 10;
        private int pageIndex = 1;
        private OnPageListener mListener;
        private Context mContext;
        private Type mType;

        public Pager bulider(Context context, Type type) {
            mContext = context;
            mType = type;
            valida();
            return new Pager();
        }

        public Builder setOnPageListener(OnPageListener listener) {
            this.mListener = listener;
            return builder;
        }

        private HashMap<String, Object> params = new HashMap<>(5);

        public Builder setUrl(String url) {

            this.url = url;
            Log.i("你好",url+"");
            return builder;
        }

        public Builder putParams(String key, Object value) {
            params.put(key, value);
            return builder;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return builder;
        }

        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout) {
            this.mRefreshLayout = refreshLayout;
            return builder;
        }

        public Builder setLoadMore(boolean loadMore) {
            Log.i("你好1",loadMore+"");
            this.canLoadMore = loadMore;
            Log.i("你好",loadMore+"");
            return builder;
        }
        private  void  valida(){
            if (mContext==null){
                throw new RuntimeException("context is null");
            }
            if (url==null){
                throw  new RuntimeException("url is null");
            }
            if (mRefreshLayout==null){
                throw  new RuntimeException("RefreshLayout is null");
            }
        }


    }

    public interface OnPageListener<T> {
        void load(List<T> datas, int totalPage, int totalCount);

        void refresh(List<T> datas, int totalPage, int totalCount);

        void loadMore(List<T> datas, int totalPage, int totalCount);
    }

    class RequestCallBack<T> extends SpotsCallBack<Page<T>> {


        public RequestCallBack(Context context) {
            super(context);
            super.mType =builder.mType;
        }

        @Override
        public void onSucess(Response response, Page<T> page) {
            builder.pageIndex = page.getCurrentPage();
            builder.totaPage = page.getTotalPage();
            showDatas(page.getList(), builder.totaPage, page.getTotalCount());
        }

        @Override
        public void onError(Response response, int code, Exception e) {

        }
    }
}
