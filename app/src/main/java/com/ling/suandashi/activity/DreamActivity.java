package com.ling.suandashi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ling.suandashi.R;
import com.ling.suandashi.adapter.DreamListAdapter;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.data.entity.ArticleBean;
import com.ling.suandashi.data.request.DreamListRequest;
import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.data.request.tools.APIException;
import com.ling.suandashi.data.request.tools.ErrorTools;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.data.request.tools.ResponseListener;
import com.ling.suandashi.net.HttpRequestUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DreamActivity extends BasicActivity{

    @BindView(R.id.base_title_title_tv)
    TextView title;

    @BindView(R.id.dream_search_et)
    EditText search_rt;
    @BindView(R.id.dream_rv)
    RecyclerView mRecyclerView;

    DreamListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);
        ButterKnife.bind(this);
        title.setText("周公解梦");
        loadData();
    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DreamListRequest request = new DreamListRequest();
        HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new ResponseListener<List<ArticleBean>>() {

            @Override
            public void onResponse(List<ArticleBean> obj) {
                mAdapter = new DreamListAdapter(DreamActivity.this,obj);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new DreamListAdapter.MyItemClickListener() {
                    @Override
                    public void onItemClick(View v, int positon, ArticleBean data) {
                        Intent intent = new Intent(DreamActivity.this,DreamDetailActivity.class);
                        intent.putExtra(DreamDetailActivity.dreamId,data.id);
                        intent.putExtra(DreamDetailActivity.dreamType,DreamDetailActivity.dream_detail_title);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(RequestData params, RequestResult result) {
                ErrorTools.doError(DreamActivity.this,params,result.getMessage(),result.getStatus());
            }

            @Override
            public void onNetworkError(APIException error) {
                ErrorTools.doNetError(DreamActivity.this,error);
            }
        });
        requestUtils.setShowLoader(false);
        requestUtils.execute();
    }

    @OnClick({R.id.base_title_back ,R.id.dream_jiemeng_tv,R.id.dream_search_et})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_title_back:
                collapseSoftInputMethod(view);
                finish();
                break;
            case R.id.dream_search_et:
                showSoftInputMethod(search_rt);
                break;
            case R.id.dream_jiemeng_tv://解梦
                String search = search_rt.getText().toString();
                if(TextUtils.isEmpty(search)){
                    Toast.makeText(this,"请输入您要搜索的梦境",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(this,DreamSearchActivity.class);
                    intent.putExtra("search",search);
                    startActivity(intent);
                    collapseSoftInputMethod(view);
                }
                break;

        }
    }


}
