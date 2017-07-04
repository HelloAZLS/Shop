package ysg.gdcp.cn.myrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 08:20.
 *
 * @author ysg
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<String> mDatas;
    private onItemClickListener mListener;

    public void setOnItemClickItem(onItemClickListener listener){

        mListener = listener;
    }

    public MyAdapter(List<String> datas) {

        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mMtext.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        Log.i("Nihao1",mDatas.size()+"");
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mMtext;

        public ViewHolder(View itemView) {
            super(itemView);
            mMtext = (TextView) itemView.findViewById(R.id.text);
            mMtext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null){
                        mListener.onclickListener(v,getPosition(),mDatas.get(getPosition()));
                    }
                }
            });
        }
    }

    interface  onItemClickListener{
        void onclickListener(View v,int position,String  city);
    }
}
