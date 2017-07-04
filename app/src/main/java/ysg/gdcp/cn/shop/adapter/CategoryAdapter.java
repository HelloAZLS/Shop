package ysg.gdcp.cn.shop.adapter;

import android.content.Context;

import java.util.List;

import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.bean.Category;

/**
 * Created by Administrator on 2017/7/4 09:36.
 *
 * @author ysg
 */

 public class CategoryAdapter extends SimpleAdapter<Category> {
    public CategoryAdapter(List<Category> datas, Context context) {
        super(datas, context, R.layout.template_single_text);
    }

    @Override
    public void bindData(BaseViewHolder holder, Category category) {
       holder.getTextView(R.id.textView).setText(category.getName());
    }
}
