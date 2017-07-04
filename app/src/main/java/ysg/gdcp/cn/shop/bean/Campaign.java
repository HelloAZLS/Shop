package ysg.gdcp.cn.shop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/6 17:17.
 *
 * @author ysg
 */

public class Campaign implements Serializable {
    private Long id;
    private String title;
    private String imgUrl;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
