package ysg.gdcp.cn.shop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/6 09:15.
 *
 * @author ysg
 */

public class BaseBean  implements Serializable {
    protected   long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
