package com.ling.suandashi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ling.suandashi.R;
import com.ling.suandashi.SplashActivity;
import com.ling.suandashi.base.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VersionActivity extends BasicActivity{

    @BindView(R.id.base_title_title_tv)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        ButterKnife.bind(this);
        title.setText("版本说明");
    }

    @OnClick({R.id.version_secret_rl,R.id.version_agreement_rl})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.version_agreement_rl:
                Intent intent = new Intent(this, AgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.version_secret_rl:
                Intent intent1 = new Intent(this, SecretActivity.class);
                startActivity(intent1);
                break;
            case R.id.base_title_back:
                finish();
                break;
        }
    }


}
