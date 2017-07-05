package ysg.gdcp.cn.shop.bean;

import java.io.Serializable;



/**
 * Created by Administrator on 2017/7/5 08:24.
 *
 * @author ysg
 */


public class ShoppingCart extends Wares implements Serializable {


    private int count;
    private boolean isChecked=true;



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }





}
