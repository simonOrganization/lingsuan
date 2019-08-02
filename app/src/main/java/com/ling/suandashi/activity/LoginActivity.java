package com.ling.suandashi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ling.suandashi.R;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.base.DefaultListener;
import com.ling.suandashi.data.request.GetCodeRequest;
import com.ling.suandashi.data.request.LoginRequest;
import com.ling.suandashi.net.HttpRequestUtils;
import com.ling.suandashi.tools.MatcherUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BasicActivity{

    @BindView(R.id.login_phone_et)
    EditText phone;
    @BindView(R.id.login_yzm_et)
    EditText code;
    @BindView(R.id.login_yzm_tv)
    TextView yzm;
    @BindView(R.id.login_login_tv)
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_title_back,R.id.login_weixin,R.id.login_login_tv,R.id.login_yzm_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_title_back:
                finish();
                break;
            case R.id.login_login_tv://登录
//                goLogin();
                break;
            case R.id.login_yzm_tv://获取验证码
//                getCode();
                break;
            case R.id.login_weixin://微信登录
                break;
        }
    }

    private void getCode() {
        if (MatcherUtils.isPhoneNumber(phone.getText().toString())){
            GetCodeRequest request = new GetCodeRequest(phone.getText().toString());
            HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new DefaultListener(this) {
                @Override
                public void onResponse(Object obj) {

                }
            });
            requestUtils.execute();
        }
    }

    private void goLogin() {
        String telephone = phone.getText().toString();
        String phonecode = code.getText().toString();
        if(isValidate(telephone,phonecode)){
            LoginRequest request = new LoginRequest(telephone,phonecode);
            HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new DefaultListener(this) {
                @Override
                public void onResponse(Object obj) {

                }
            });
            requestUtils.execute();

        }
    }

    private boolean isValidate(String username, String password) {
        if (!MatcherUtils.isPhoneNumber(username)) {
            Toast.makeText(LoginActivity.this, R.string.account_must_phone, Toast.LENGTH_SHORT).show();
            phone.requestFocus();
            return false;
        }
        if (MatcherUtils.isNull(password)) {
            Toast.makeText(LoginActivity.this, R.string.password_length, Toast.LENGTH_SHORT).show();
            code.requestFocus();
            return false;
        }
        return true;
    }

}
