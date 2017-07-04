package ysg.gdcp.cn.shop.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.bean.Wares;

/**
 * Created by Administrator on 2017/7/4 08:59.
 *
 * @author ysg
 */

public class HWAdapter extends  SimpleAdapter<Wares> {
    public HWAdapter(List<Wares> datas, Context context) {
        super(datas, context, R.layout.template_hot_wares);
    }

    @Override
    public void bindData(BaseViewHolder holder, Wares wares) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getVIew(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        holder.getTextView(R.id.text_title).setText(wares.getName());
        holder.getTextView(R.id.text_price).setText(wares.getPrice().toString());
    }
}
