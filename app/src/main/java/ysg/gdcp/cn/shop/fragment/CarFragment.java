package ysg.gdcp.cn.shop.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ysg.gdcp.cn.shop.MainActivity;
import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.Utils.CartProvider;
import ysg.gdcp.cn.shop.adapter.CartAdapter;
import ysg.gdcp.cn.shop.adapter.DividerItemDecortion;
import ysg.gdcp.cn.shop.bean.ShoppingCart;
import ysg.gdcp.cn.shop.view.MyToolbar;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class CarFragment extends Fragment implements View.OnClickListener {


    private RecyclerView mRecyclerView;
    private View mView;
    private CheckBox mCheckBox;
    private TextView mTxtTotal;
    private Button mBtnOrder;
    private Button mBtnDel;
    private CartProvider mCartProvider;
    private CartAdapter mCartAdapter;
    private MyToolbar mToolbar;
    private MyToolbar mMainToolbar;
    private Button mButton;
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_CAMPLATE = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_cart, container, false);
        mCartProvider = new CartProvider(getContext());
        initView();

        showData();
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hidTollbar();
    }

    private void hidTollbar() {
        if (getContext() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getContext();
            mMainToolbar = (MyToolbar) activity.findViewById(R.id.toolbar_main);
            mMainToolbar.setVisibility(View.GONE);
//            mToolbar.setTitle("购物车");
//            mToolbar.getRightButton().setVisibility(View.VISIBLE);
        }
    }

    private void showData() {
        List<ShoppingCart> carts = mCartProvider.getAll();
        mCartAdapter = new CartAdapter(getContext(), carts, mCheckBox, mTxtTotal);
        mRecyclerView.setAdapter(mCartAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecortion(getContext(), DividerItemDecortion.VERTICAL_LIST));
    }


    private void initView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        mCheckBox = (CheckBox) mView.findViewById(R.id.checkbox_all);
        mTxtTotal = (TextView) mView.findViewById(R.id.txt_total);
        mBtnOrder = (Button) mView.findViewById(R.id.btn_order);
        mBtnDel = (Button) mView.findViewById(R.id.btn_del);
        mToolbar = (MyToolbar) mView.findViewById(R.id.toolbar);
        mBtnDel.setOnClickListener(this);
        btnEditor();


    }

    public void btnEditor() {
        hidTollbar();
        mToolbar.hideSearchView();
        mButton = mToolbar.getRightButton();
        mButton.setText("编辑");
        mButton.setTextColor(Color.WHITE);
        mButton.setVisibility(View.VISIBLE);
        mButton.setOnClickListener(this);
        mButton.setTag(ACTION_EDIT);

    }

    public void refreshData() {
        mCartAdapter.clearData();
        List<ShoppingCart> carts = mCartProvider.getAll();
        mCartAdapter.addData(carts);
        mCartAdapter.showTotalPrice();
    }

    private void showDelControl() {
        mToolbar.getRightButton().setText("完成");
        mTxtTotal.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mToolbar.getRightButton().setTag(ACTION_CAMPLATE);
        mCartAdapter.checkAll_None(false);
        mCheckBox.setChecked(false);
    }

    private void hideDelControl() {
        mToolbar.getRightButton().setText("编辑");
        mTxtTotal.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);
        mBtnDel.setVisibility(View.GONE);
        mToolbar.getRightButton().setTag(ACTION_EDIT);
        mCartAdapter.checkAll_None(true);
        mCheckBox.setChecked(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_rightButton:
                int action = (int) view.getTag();
                if (action == ACTION_EDIT) {
                    showDelControl();
                } else if (action == ACTION_CAMPLATE) {
                    hideDelControl();
                }
                break;
            case R.id.btn_del:
                mCartAdapter.delCart();
                break;
        }
    }
}
