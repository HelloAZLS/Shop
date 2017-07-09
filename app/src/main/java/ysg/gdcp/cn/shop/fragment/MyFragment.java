package ysg.gdcp.cn.shop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import ysg.gdcp.cn.shop.MainActivity;
import ysg.gdcp.cn.shop.MyApplication;
import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.Utils.Contants;
import ysg.gdcp.cn.shop.activity.LoginActivity;
import ysg.gdcp.cn.shop.bean.User;
import ysg.gdcp.cn.shop.view.MyToolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener {


    private View mView;
    private CircleImageView mCircleImageView;
    private TextView mTextView;
    private MyToolbar mMainToolbar;
    private Button mBtnLogout;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_my, container, false);
        initViews();
        init();
        hidTollbar();
        return mView;
    }

    private void init() {
        User user = MyApplication.getInstance().getUser();
        showUser(user);
    }
    private void hidTollbar() {
        if (getContext() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getContext();
            mMainToolbar = (MyToolbar) activity.findViewById(R.id.toolbar_main);
            mMainToolbar.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        mCircleImageView = (CircleImageView) mView.findViewById(R.id.img_head);
        mTextView = (TextView) mView.findViewById(R.id.txt_username);
        mBtnLogout = (Button) mView. findViewById(R.id.btn_logout);
        mCircleImageView.setOnClickListener(this);
        mTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_head:
            case R.id.txt_username:
                Intent intent =new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, Contants.REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        User user = MyApplication.getInstance().getUser();
        showUser(user);

    }

    private void showUser(User user) {
        if (user!=null){
            mTextView.setText(user.getUsername());
            Picasso.with(getActivity()).load(user.getLogo_url()).into(mCircleImageView);
        }else {
            mTextView.setText(R.string.to_login);
            mBtnLogout.setEnabled(false);
        }
    }
}
