package ysg.gdcp.cn.shop.listener;

import android.content.Context;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import dmax.dialog.SpotsDialog;

/**
 * Created by Administrator on 2017/6/6 12:03.
 *
 * @author ysg
 */

public abstract class SpotsCallBack<T> extends OkHttpBaseCallBack<T> {

    SpotsDialog mDialog;

    public SpotsCallBack(Context context) {
        mDialog = new SpotsDialog(context);
    }

    public void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void hideDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public void setMessage(String message) {
        if (mDialog != null) {
            mDialog.setMessage(message);
        }
    }

    @Override
    public void onRequestBefore(Request request) {
        showDialog();
    }

    @Override
    public void onFailure(Request request, IOException e) {
        hideDialog();
    }

    @Override
    public void onResponse(Response response) {
        hideDialog();
    }
}
