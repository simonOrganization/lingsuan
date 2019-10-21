package com.ling.suandashi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ling.suandashi.R;
import com.ling.suandashi.adapter.OrderListAdapter;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.base.DefaultListener;
import com.ling.suandashi.data.entity.OrderBean;
import com.ling.suandashi.data.request.OrderListRequest;
import com.ling.suandashi.net.HttpRequestUtils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends BasicActivity{

    @BindView(R.id.order_all_tv)//全部订单
    TextView all_tv;
    @BindView(R.id.order_all_img)
    ImageView all_img;

    @BindView(R.id.order_complete_tv)//已完成订单
    TextView complete_tv;
    @BindView(R.id.order_complete_img)
    ImageView complete_img;

    @BindView(R.id.order_no_tv)//未完成订单
    TextView no_tv;
    @BindView(R.id.order_no_img)
    ImageView no_img;

    @BindView(R.id.order_nolist_rl)//无订单数据展示
    RelativeLayout nolist_rl;
    @BindView(R.id.order_rv)
    RecyclerView mRecyclerView;

    OrderListAdapter mAdapter;
    OrderBean mOrderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        loadData(1);
    }

    private void loadData(int status) {
        nolist_rl.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        OrderListRequest request = new OrderListRequest(status);
        HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new DefaultListener(this) {
            @Override
            public void onResponse(Object obj) {
                if(obj instanceof OrderBean){
                    mOrderBean = (OrderBean) obj;
                    if(mOrderBean != null && mOrderBean.list !=null){
                        mAdapter = new OrderListAdapter(OrderActivity.this,mOrderBean.list);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(new OrderListAdapter.MyItemClickListener() {
                            @Override
                            public void onItemClick(View v, int positon, OrderBean.OrderList data) {
                                Intent intent = new Intent(OrderActivity.this, WebActivity.class);
                                intent.putExtra(WebActivity.KEY_TITLE,data.title);
                                intent.putExtra(WebActivity.URL_KEY,data.url);
                                startActivity(intent);
                            }
                        });
                    }

                    if(mOrderBean != null && mOrderBean.list !=null && mOrderBean.list.size() > 0){
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }else {
                        nolist_rl.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
        requestUtils.execute();
    }

    @OnClick({R.id.base_title_back,R.id.order_all_rl,R.id.order_complete_rl,R.id.order_no_rl})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_title_back:
                finish();
                break;
            case R.id.order_all_rl:
                reset();
                all_tv.setTextColor(Color.parseColor("#7A3401"));
                all_img.setVisibility(View.VISIBLE);
                loadData(1);
                break;
            case R.id.order_complete_rl:
                reset();
                complete_tv.setTextColor(Color.parseColor("#7A3401"));
                complete_img.setVisibility(View.VISIBLE);
                loadData(2);
                break;
            case R.id.order_no_rl:
                reset();
                no_tv.setTextColor(Color.parseColor("#7A3401"));
                no_img.setVisibility(View.VISIBLE);
                loadData(3);
                break;
        }
    }

    public void reset(){
        all_tv.setTextColor(Color.parseColor("#807A3401"));
        complete_tv.setTextColor(Color.parseColor("#807A3401"));
        no_tv.setTextColor(Color.parseColor("#807A3401"));

        all_img.setVisibility(View.GONE);
        complete_img.setVisibility(View.GONE);
        no_img.setVisibility(View.GONE);
    }
}
