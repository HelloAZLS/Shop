package ysg.gdcp.cn.shop.listener;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/6/6 10:38.
 *
 * @author ysg
 */

public abstract class OkHttpBaseCallBack<T> {

    public Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public OkHttpBaseCallBack() {
        mType = getSuperclassTypeParameter(getClass());
    }


    public abstract void onRequestBefore(Request request);

    public abstract void onFailure(Request request, IOException  e);

    public abstract void onSucess(Response response,T t);
    public abstract void onResponse(Response response);

    public abstract void onError(Response response, int code, Exception e);
}
