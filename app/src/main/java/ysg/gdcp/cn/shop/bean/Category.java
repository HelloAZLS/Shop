package ysg.gdcp.cn.shop.bean;

/**
 * Created by Administrator on 2017/6/6 09:13.
 *
 * @author ysg
 */
public class Category  extends BaseBean{

    private String name;

    public Category() {
    }

    public Category(String name) {

        this.name = name;
    }

    public Category(long id ,String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
