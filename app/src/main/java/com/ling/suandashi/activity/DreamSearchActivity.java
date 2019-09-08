package com.ling.suandashi.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ling.suandashi.R;
import com.ling.suandashi.adapter.DreamResearchAdapter;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.data.entity.ArticleBean;
import com.ling.suandashi.data.request.DreamSearchRequest;
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

public class DreamSearchActivity extends BasicActivity{

    @BindView(R.id.dream_search_search)
    EditText search;
    @BindView(R.id.dream_search_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.dream_search_rl)
    RelativeLayout no_research_rl;

    private String search_content;
    DreamResearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_search);
        ButterKnife.bind(this);

        search_content = getIntent().getStringExtra("search");
        search.setText(search_content);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(TextUtils.isEmpty(search.getText().toString())){
                        Toast.makeText(DreamSearchActivity.this,"请输入您要搜索的梦境",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    loadData(search.getText().toString());
                    collapseSoftInputMethod(v);
                    search.clearFocus();
                }
                return false;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        loadData(search_content);
    }

    private void loadData(String content) {
        DreamSearchRequest request = new DreamSearchRequest(content);
        HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new ResponseListener<List<ArticleBean>>() {
            @Override
            public void onResponse(List<ArticleBean> obj) {
                if(obj != null && obj.size() > 0){
                    no_research_rl.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mAdapter = new DreamResearchAdapter(DreamSearchActivity.this,obj);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new DreamResearchAdapter.MyItemClickListener() {
                        @Override
                        public void onItemClick(View v, int positon, ArticleBean data) {

                        }
                    });
                }else {
                    no_research_rl.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(RequestData params, RequestResult result) {
                ErrorTools.doError(DreamSearchActivity.this,params,result.getMessage(),result.getStatus());
            }

            @Override
            public void onNetworkError(APIException error) {
                ErrorTools.doNetError(DreamSearchActivity.this,error);
            }
        });
        requestUtils.setShowLoader(false);
        requestUtils.execute();
    }

    @OnClick({R.id.base_title_back, R.id.dream_search_delete_rl, R.id.dream_search_search, R.id.dream_search_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_title_back:
            case R.id.dream_search_iv:
                collapseSoftInputMethod(view);
                finish();
                break;
            case R.id.dream_search_search:
                showSoftInputMethod(search);
                break;
            case R.id.dream_search_delete_rl://删除搜索内容
                search.setText("");
                break;
        }
    }


}
