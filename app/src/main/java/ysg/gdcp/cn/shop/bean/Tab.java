package ysg.gdcp.cn.shop.bean;

/**
 * Created by Administrator on 2017/6/1 19:53.
 *
 * @author ysg
 */

public class Tab {

    private int title;
    private int id;
    private Class mFragment;

    public Tab() {
    }

    public Tab(int title, int id, Class fragment) {
        this.title = title;
        this.id = id;
        mFragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class getFragment() {
        return mFragment;
    }

    public void setFragment(Class fragment) {
        mFragment = fragment;
    }
}
