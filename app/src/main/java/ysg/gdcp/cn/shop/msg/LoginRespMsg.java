package ysg.gdcp.cn.shop.msg;

/**
 * Created by Administrator on 2017/7/9 08:21.
 *
 * @author ysg
 */




public class LoginRespMsg<T> extends BaseRespMsg {


    private String token;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

