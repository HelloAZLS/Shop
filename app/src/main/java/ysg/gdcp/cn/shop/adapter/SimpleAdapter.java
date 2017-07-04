package ysg.gdcp.cn.shop.adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4 08:47.
 *
 * @author ysg
 */

public class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {
    public SimpleAdapter(List<T> datas, Context context, int layoutID) {
        super(datas, context, layoutID);
    }

    @Override
    public void bindData(BaseViewHolder holder, T t) {

    }
}
