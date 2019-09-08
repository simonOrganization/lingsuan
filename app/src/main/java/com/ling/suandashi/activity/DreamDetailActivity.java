package com.ling.suandashi.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ling.suandashi.R;
import com.ling.suandashi.adapter.DreamListAdapter;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.base.DefaultListener;
import com.ling.suandashi.data.entity.ArticleBean;
import com.ling.suandashi.data.entity.DreamDetailBean;
import com.ling.suandashi.data.request.DreamListRequest;
import com.ling.suandashi.data.request.DreamTitleDetailRequest;
import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.data.request.tools.APIException;
import com.ling.suandashi.data.request.tools.ErrorTools;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.data.request.tools.ResponseListener;
import com.ling.suandashi.net.HttpRequestUtils;
import com.ling.suandashi.view.ZWebView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DreamDetailActivity extends BasicActivity{

    @BindView(R.id.base_title_title_tv)
    TextView title;

    @BindView(R.id.dream_detail_web)
    ZWebView mWebView;

    public static final int dream_detail_title = 100;
    public static final int dream_detail_other = 101;
    public static final String dreamId = "DREAMID";
    public static final String dreamType = "DREAMTYPE";

    int id = 0;
    int type = 0;
    DreamDetailBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_detail);
        ButterKnife.bind(this);
        title.setText("解梦详情");
        id = getIntent().getIntExtra(dreamId,0);
        type = getIntent().getIntExtra(dreamType,dream_detail_other);
        loadData();
    }

    private void loadData() {
        DreamTitleDetailRequest request = new DreamTitleDetailRequest(id,1);
        HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new DefaultListener(this) {
            @Override
            public void onResponse(Object obj) {
                if(obj instanceof DreamDetailBean){
                    mBean = (DreamDetailBean) obj;
                    mWebView.loadDataWithBaseURL(null,"<html><body>"+mBean.content+"</body></html>","text/html","utf-8",null);
                }
            }
        });
        requestUtils.setShowLoader(false);
        requestUtils.execute();
    }

    @OnClick({R.id.base_title_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_title_back:
                finish();
                break;
        }
    }


}
