package ysg.gdcp.cn.shop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.squareup.okhttp.Response;

import java.util.HashMap;
import java.util.Map;

import ysg.gdcp.cn.shop.MyApplication;
import ysg.gdcp.cn.shop.R;
import ysg.gdcp.cn.shop.Utils.Contants;
import ysg.gdcp.cn.shop.Utils.DESUtils;
import ysg.gdcp.cn.shop.Utils.OkHttpUtils;
import ysg.gdcp.cn.shop.bean.User;
import ysg.gdcp.cn.shop.listener.SpotsCallBack;
import ysg.gdcp.cn.shop.msg.LoginRespMsg;
import ysg.gdcp.cn.shop.view.ClearEditText;
import ysg.gdcp.cn.shop.view.MyToolbar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private MyToolbar mToolbar;
    private ClearEditText mEditTextPhone;
    private ClearEditText mEditTextPwds;
    private OkHttpUtils okhttpUtils = OkHttpUtils.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
    }

    private void initViews() {
        mToolbar = (MyToolbar) findViewById(R.id.toolbar);
        mEditTextPhone = (ClearEditText) findViewById(R.id.etxt_phone);
        mEditTextPwds = (ClearEditText) findViewById(R.id.etxt_pwd);
        findViewById(R.id.btn_login).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String phone =mEditTextPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(this, "电话号码不能为空" , Toast.LENGTH_SHORT).show();
                    return;
                }
                String pwd =mEditTextPwds.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)){
                    Toast.makeText(this, "密码不能为空" , Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> map =new HashMap(2);
                map.put("phone",phone);
                map.put("password", DESUtils.encode(Contants.DES_KEY,pwd));


               okhttpUtils.post(Contants.API.LOGIN, map, new SpotsCallBack<LoginRespMsg<User>>(this) {

                   @Override
                   public void onSucess(Response response, LoginRespMsg<User> userLoginRespMsg) {
                       MyApplication.getInstance().putUser(userLoginRespMsg.getData(),userLoginRespMsg.getToken());
                       setResult(RESULT_OK);
                       finish();
                   }

                   @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
                break;
        }
    }
}
