package ysg.gdcp.cn.shop.Utils;

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ysg.gdcp.cn.shop.bean.ShoppingCart;
import ysg.gdcp.cn.shop.bean.Wares;

/**
 * Created by Administrator on 2017/7/5 08:23.
 *
 * @author ysg
 */

public class CartProvider {

    private SparseArray<ShoppingCart> datas = null;
    private Context mContext;
    public static final String CART_JSON = "cart_json";

    public CartProvider(Context context) {
        mContext = context;
        datas = new SparseArray<>(10);
        listToSpares();

    }

    public void put(ShoppingCart cart) {
        ShoppingCart temp = datas.get(cart.getId().intValue());
        if (temp != null) {
            temp.setCount(temp.getCount() + 1);
        } else {
            temp = cart;
            temp.setCount(1);
        }
        datas.put(cart.getId().intValue(), temp);
        commit();
    }
    public void put(Wares wares) {

        ShoppingCart cart =converData(wares);
        put(cart);
    }

    public void update(ShoppingCart cart) {
        datas.put(cart.getId().intValue(), cart);
        commit();
    }

    public void detele(ShoppingCart cart) {
        datas.delete(cart.getId().intValue());
        commit();
    }

    public List<ShoppingCart> getAll() {

        return getDataFromLocal();
    }



    public void commit() {
        List<ShoppingCart> list = sparesToList();
        PreferencesUtils.putString(mContext, CART_JSON, JsonUtils.toJSON(list));
    }

    public void listToSpares(){
        List<ShoppingCart> carts = getDataFromLocal();
        if (carts!=null&&carts.size()>0){
            for (ShoppingCart cart : carts) {
                datas.put(cart.getId().intValue(),cart);
            }
        }
    }

    private List<ShoppingCart> sparesToList() {

        int size = datas.size();
        List<ShoppingCart> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(datas.valueAt(i));
        }
        return list;
    }
    public List<ShoppingCart> getDataFromLocal() {
        String result = PreferencesUtils.getString(mContext, CART_JSON);
        List<ShoppingCart> carts = null;
        if (result != null) {
            carts = JsonUtils.fromJson(result, new TypeToken<List<ShoppingCart>>() {
            }.getType());
        }

        return carts;
    }

    public ShoppingCart converData(Wares wares) {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(wares.getId());
        cart.setDescription(wares.getDescription());
        cart.setImgUrl(wares.getImgUrl());
        cart.setName(wares.getName());
        cart.setPrice(wares.getPrice());
        return cart;
    }
}
