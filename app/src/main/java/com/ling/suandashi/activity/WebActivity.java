package com.ling.suandashi.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ling.suandashi.R;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.view.ZWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BasicActivity {

    @BindView(R.id.base_title_title_tv)
    TextView title;
    @BindView(R.id.lswebview)
    ZWebView mWebView;

    public static final String KEY_TITLE = "key_title";
    public static final String URL_KEY = "url";
    private String url;
    private String transferTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        transferTitle = getIntent().getStringExtra(KEY_TITLE);
        title.setText(transferTitle);

        url = getIntent().getStringExtra(URL_KEY);
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }

        mWebView.setWebViewBackListener(new ZWebView.WebViewBackListenerImpl(){
            @Override
            public void webFinish() {
                finish();
            }
            @Override
            public void initTitle(String h5title) {
                transferTitle = getIntent().getStringExtra(KEY_TITLE);
                if(!TextUtils.isEmpty(transferTitle)){
                    title.setText(transferTitle);
                }else{
                    title.setText(h5title);
                }
            }
        });
    }

    @OnClick({R.id.base_title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_title_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(mWebView!=null){
            ViewGroup parent  = (ViewGroup)mWebView.getParent();
            if(parent!=null){
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mWebView!=null){
            mWebView.resumeTimers();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mWebView!=null){
            mWebView.pauseTimers();
        }
    }

}
