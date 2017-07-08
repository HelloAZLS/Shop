package ysg.gdcp.cn.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4 08:18.
 *
 * @author ysg
 */

public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected int mLayoutID;

    protected OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener =listener;
    }

    public BaseAdapter(List<T> datas, Context context, int layoutID) {
        mDatas = datas;
        mContext = context;
        mLayoutID = layoutID;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(mLayoutID, null, false);

        return new BaseViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = getItem(position);

        bindData(holder, t);

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getData(int position) {
        return mDatas.get(position);
    }

    public List<T> getData(){
        return  mDatas;
    }

    public void clearData() {
        if (mDatas!=null){
            mDatas.clear();
            notifyItemRangeChanged(0, mDatas.size());
        }

    }

    public void refreshData(List<T> list){

        if(list !=null && list.size()>0){

            clearData();
            int size = list.size();
//            Log.i("数据大小",size+"");
//            for (int i=0;i<size;i++){
//                mDatas.add(i,list.get(i));
//                mDatas.addAll(list);
//                notifyItemInserted(i);
//            }
            mDatas.addAll(list);
            notifyItemRangeChanged(0,size-1);
//            Log.i("数据大小3",size+"");
        }
    }

    public void loadMoreData(List<T> list){

        if(list !=null && list.size()>0){

            int size = list.size();
            int begin = mDatas.size();
            for (int i=0;i<size;i++){
                mDatas.add(list.get(i));
                notifyItemInserted(i+begin);
            }

        }

    }

    public void addData(List<T> datas) {
        addData(0, datas);
    }

    public void addData(int position, List<T> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyItemRangeChanged(position, mDatas.size());
        }
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public abstract void bindData(BaseViewHolder holder, T t);


}
