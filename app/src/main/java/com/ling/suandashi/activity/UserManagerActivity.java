package com.ling.suandashi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ling.suandashi.R;
import com.ling.suandashi.adapter.UserManagerAdapter;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.view.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserManagerActivity extends BasicActivity{

    @BindView(R.id.base_title_title_tv)
    TextView title;
    @BindView(R.id.user_manager_root)
    RelativeLayout root;//用户管理界面
    @BindView(R.id.user_manager_nouser_rl)
    RelativeLayout no_user_rl;//暂无用户页
    @BindView(R.id.user_manager_rv)
    SlideRecyclerView mRecyclerView;//用户管理


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        ButterKnife.bind(this);
        title.setText("用户管理");

        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            lists.add("测试数据" + i);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        UserManagerAdapter adapter = new UserManagerAdapter(this,lists);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new UserManagerAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View v, int positon, String data) {
                lists.remove(positon);
                adapter.notifyDataSetChanged();
                mRecyclerView.closeMenu();
            }
        });
    }

    @OnClick({R.id.base_title_back ,R.id.user_manager_new_tv})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.user_manager_new_tv://新增用户
                break;
        }
    }


}
