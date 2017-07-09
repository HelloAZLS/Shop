package ysg.gdcp.cn.shop.msg;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/9 08:20.
 *
 * @author ysg
 */




public class BaseRespMsg implements Serializable {

    public final static int STATUS_SUCCESS=1;
    public final static int STATUS_ERROR=0;
    public final static String MSG_SUCCESS="success";

    protected  int status=STATUS_SUCCESS;
    protected  String message;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
