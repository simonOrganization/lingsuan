package com.ling.suandashi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ling.suandashi.R;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.data.request.LoginRequest;
import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.data.request.tools.APIException;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.data.request.tools.ResponseListener;
import com.ling.suandashi.net.HttpRequestUtils;

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

    @OnClick({R.id.login_title_back,R.id.login_weixin})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_title_back:
                finish();
                break;
            case R.id.login_weixin://微信登录
                LoginRequest request = new LoginRequest();
                HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new ResponseListener() {
                    @Override
                    public void onResponse(Object obj) {

                    }

                    @Override
                    public void onFailure(RequestData params, RequestResult result) {

                    }

                    @Override
                    public void onNetworkError(APIException error) {

                    }
                });
                requestUtils.execute();
                break;
        }
    }


}
