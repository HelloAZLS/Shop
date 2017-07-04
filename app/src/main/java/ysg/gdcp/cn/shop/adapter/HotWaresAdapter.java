package ysg.gdcp.cn.shop.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.bean.Wares;

/**
 * Created by Administrator on 2017/6/6 19:50.
 *
 * @author ysg
 */

public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHolder> {


    private List<Wares> mDatas;

    public HotWaresAdapter(List<Wares> mDatas) {

        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.template_hot_wares, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wares wares = mDatas.get(position);
        holder.tvPrice.setText(wares.getName());
        holder.draweeView.setImageURI(Uri.parse(wares.getImgUrl()));
        holder.tvPrice.setText("价格：" + wares.getPrice());
    }

    public Wares getData(int position) {
        return mDatas.get(position);
    }

    public List<Wares> getData(){
        return  mDatas;
    }

    public void clearData() {
        mDatas.clear();
        notifyItemRangeChanged(0, mDatas.size());
    }

    public void addData(List<Wares> datas) {
        addData(0, datas);
    }

    public void addData(int position, List<Wares> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyItemRangeChanged(position, mDatas.size());
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView draweeView;
        TextView tvTitle;
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.drawee_view);
            tvTitle = (TextView) itemView.findViewById(R.id.text_title);
            tvPrice = (TextView) itemView.findViewById(R.id.text_price);
        }
    }
}
