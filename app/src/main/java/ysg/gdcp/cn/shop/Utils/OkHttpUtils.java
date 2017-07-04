package ysg.gdcp.cn.shop.Utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ysg.gdcp.cn.shop.listener.OkHttpBaseCallBack;

/**
 * Created by Administrator on 2017/6/6 10:34.
 *
 * @author ysg
 */

public class OkHttpUtils {

    private Gson mGson;
    private static OkHttpClient okHttpClient;
    private Handler mHandler;

    private OkHttpUtils() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mGson = new Gson();

        mHandler =new Handler(Looper.getMainLooper());

    }

    public static OkHttpUtils getInstance() {
        return new OkHttpUtils();
    }


    public void get(String url, OkHttpBaseCallBack callBack) {
        Request request = buliderREquest(url, null, HttpMethodType.GET);
        doRequest(request, callBack);

    }

    public void post(String url, Map<String, String> params, OkHttpBaseCallBack callBack) {
        Request request = buliderREquest(url, params, HttpMethodType.POST);
        doRequest(request, callBack);

    }

    public void doRequest(final Request request, final OkHttpBaseCallBack callBack) {
        callBack.onRequestBefore(request);

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callBack.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                callBack.onResponse(response);
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (callBack.mType == String.class) {
//                        callBack.onSucess(response, request);
                        callBackSucess(callBack,response,request);
                    } else {
                        try {
                            Object object = mGson.fromJson(result, callBack.mType);
//                            callBack.onSucess(response, object);
                            callBackSucess(callBack,response,object);
                        } catch (JsonParseException e) {
//                            callBack.onError(response, response.code(),e);
                            callBackError(callBack,response,e);
                        }

                    }

                } else {
                    callBackError(callBack,response,null);

                }
            }
        });
    }

    private Request buliderREquest(String url, Map<String, String> params, HttpMethodType type) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (type == HttpMethodType.GET) {
            builder.get();
        } else if (type == HttpMethodType.POST) {
            RequestBody body = BulidFormData(params);
            builder.post(body);
        }
        return builder.build();
    }


    private RequestBody BulidFormData(Map<String, String> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private  void callBackSucess(final OkHttpBaseCallBack callback, final  Response response, final  Object onject){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
           callback.onSucess(response,onject);
            }
        });
    }
    private  void callBackError(final OkHttpBaseCallBack callback, final  Response response,final Exception e){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response,response.code(),e);
            }
        });
    }

    enum HttpMethodType {
        GET, POST;
    }
}
