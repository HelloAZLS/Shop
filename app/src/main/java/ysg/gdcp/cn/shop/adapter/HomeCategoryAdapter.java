package ysg.gdcp.cn.shop.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.bean.Campaign;
import ysg.gdcp.cn.shop.bean.HomeCampaign;

/**
 * Created by Administrator on 2017/6/6 09:23.
 *
 * @author ysg
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    private static int VIEW_TYPE_L = 0;
    private static int VIEW_TYPE_R = 1;
    private List<HomeCampaign> mDatas;
    private Context mContext;


    private OnCampaignClickListener mListener;

    public HomeCategoryAdapter(List<HomeCampaign> datas, Context context) {
        mDatas = datas;
        mContext = context;
    }

    public void setOnCampaignClickListener(OnCampaignClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_R) {

            return new ViewHolder(View.inflate(parent.getContext(), R.layout.template_home_cardview2, null));

        }

        return new ViewHolder(View.inflate(parent.getContext(), R.layout.template_home_cardview, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCampaign homeCampaign = mDatas.get(position);

        holder.mtvTitle.setText(homeCampaign.getTitle());
        Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(holder.mImageBig);
        Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(holder.mImageSamllTop);
        Picasso.with(mContext).load(homeCampaign.getCpThree().getImgUrl()).into(holder.mImageSmallBottom);
    }

    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 0) {
            return VIEW_TYPE_R;
        } else {
            return VIEW_TYPE_L;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        TextView mtvTitle;
        ImageView mImageBig;
        ImageView mImageSamllTop;
        ImageView mImageSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);

            mtvTitle = (TextView) itemView.findViewById(R.id.text_title);
            mImageBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            mImageSamllTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            mImageSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);
            mImageBig.setOnClickListener(this);
            mImageSamllTop.setOnClickListener(this);
            mImageSmallBottom.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            HomeCampaign homeCampaign =mDatas.get(getPosition());
//            if (mListener != null) {
//                switch (v.getId()) {
//                    case R.id.imgview_big:
//                        mListener.onClick(v,homeCampaign.getCpOne());
//                        break;
//                    case R.id.imgview_small_top:
//                        mListener.onClick(v,homeCampaign.getCpTwo());
//                        break;
//                    case R.id.imgview_small_bottom:
//                        mListener.onClick(v,homeCampaign.getCpThree());
//                        break;
//                }
//            }
            anim(v);
        }

    private  void anim(final View v){
        ObjectAnimator animator =  ObjectAnimator.ofFloat(v, "rotationX", 0.0F, 360.0F)
                .setDuration(200);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                HomeCampaign homeCampaign = mDatas.get(getPosition());

                switch (v.getId()){

                    case  R.id.imgview_big:
                        mListener.onClick(v, homeCampaign.getCpOne());
                        break;

                    case  R.id.imgview_small_top:
                        mListener.onClick(v, homeCampaign.getCpTwo());
                        break;

                    case R.id.imgview_small_bottom:
                        mListener.onClick(v,homeCampaign.getCpThree());
                        break;

                }

            }
        });
        animator.start();
    }
}

    public interface OnCampaignClickListener {
        void onClick(View view, Campaign campaign);
    }
}
